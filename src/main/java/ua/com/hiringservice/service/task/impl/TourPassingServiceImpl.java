package ua.com.hiringservice.service.task.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.exception.TourPassingNotFoundException;
import ua.com.hiringservice.exception.UserHasActiveTourPassingException;
import ua.com.hiringservice.model.dto.KeycloakUserDto;
import ua.com.hiringservice.model.dto.tour.TourPassingDto;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.entity.tour.Tour;
import ua.com.hiringservice.model.entity.tour.TourPassing;
import ua.com.hiringservice.model.entity.tour.TourTask;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.tour.TourStatus;
import ua.com.hiringservice.repository.TaskPassingRepository;
import ua.com.hiringservice.repository.tour.TourPassingRepository;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.TourService;
import ua.com.hiringservice.service.task.TourPassingService;
import ua.com.hiringservice.util.mapper.TourPassingMapper;

/**
 * Implementation of the {@link TourPassingService} interface. This service class provides methods
 * for managing tour passings, including starting and updating tour passings. It interacts with
 * repositories for accessing and persisting tour passing data and uses a mapper for converting
 * entities to DTOs.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-31
 */
@SuppressWarnings("PMD.TooManyMethods")
@RequiredArgsConstructor
@Service
public class TourPassingServiceImpl implements TourPassingService {

  private final TourService tourService;
  private final TourPassingRepository tourPassingRepository;
  private final TourPassingMapper tourPassingMapper;
  private final TaskPassingRepository taskPassingRepository;
  private final KeycloakService keycloakService;

  @Override
  public TourPassingDto startTourPassing(UUID tourId, UUID userId) {
    validateOneTourPassingForUser(tourId, userId);

    final Tour tour = tourService.findTourById(tourId);

    TourPassing tourPassing =
        TourPassing.builder()
            .status(TourStatus.NOT_STARTED)
            .tour(tour)
            .keycloakUserId(userId)
            .build();
    tourPassing = tourPassingRepository.save(tourPassing);

    return tourPassingMapper.toDto(tourPassing);
  }

  @Override
  public TourPassingDto startTourPassing(UUID tourId) {
    final UUID userId = keycloakService.getKeycloakIdOfUserFromSecurityContext();
    return startTourPassing(tourId, userId);
  }

  @Override
  public TourPassingDto updateTourPassing(UUID tourId, UUID userId) {
    TourPassing tourPassing = findTourPassingByTourIdAndUserId(tourId, userId);

    final Tour tour = tourService.findTourById(tourId);

    if (allTasksGraded(userId, tour)) {
      tourPassing.setStatus(TourStatus.FINISHED);
    } else {
      tourPassing.setStatus(TourStatus.IN_PROGRESS);
    }

    tourPassing.setScore(calculateCurrentScore(tour, userId));

    tourPassing = tourPassingRepository.save(tourPassing);
    return tourPassingMapper.toDto(tourPassing);
  }

  @Override
  public TourPassingDto updateTourPassing(UUID tourId) {
    final UUID userId = keycloakService.getKeycloakIdOfUserFromSecurityContext();
    return updateTourPassing(tourId, userId);
  }

  @Override
  public TourPassingDto findTourPassingByTourId(UUID tourId) {
    final UUID userId = keycloakService.getKeycloakIdOfUserFromSecurityContext();
    final TourPassing tourPassing =
        tourPassingRepository
            .findByTourIdAndKeycloakUserId(tourId, userId)
            .orElseThrow(() -> new TourPassingNotFoundException(tourId, userId));
    return tourPassingMapper.toDto(tourPassing);
  }

  @Override
  public List<KeycloakUserDto> findAllUsersByTourIdFinishedTour(UUID tourId) {
    return tourPassingRepository.findAllByTourIdAndStatus(tourId, TourStatus.FINISHED).stream()
        .map(TourPassing::getKeycloakUserId)
        .map(keycloakService::getKeycloakUserByKeycloakId)
        .toList();
  }

  private boolean allTasksGraded(UUID userId, Tour tour) {
    final long gradedTasks = gradedTasks(userId, tour);
    return gradedTasks == tour.getTourTasks().size();
  }

  private long gradedTasks(UUID userId, Tour tour) {
    return tour.getTourTasks().stream()
        .map(
            tourTask -> {
              final List<TaskPassing> taskPassings =
                  taskPassingRepository.findAllByTaskIdAndUserKeycloakId(
                      tourTask.getTask().getId(), userId);
              return taskPassings.stream()
                  .allMatch(taskPassing -> taskPassing.getStatus().equals(PassingStatus.GRADED));
              // taskPassingStatus:   IN_PROGRESS,ANSWERED, GRADED
              // tourPassingStatus: NOT_STARTED, IN_PROGRESS, FINISHED
              // if taskPassingStatus != GRADED for all taskPassings, then tourPassingStatus =
              // IN_PROGRESS
              // if taskPassingStatus = GRADED for all taskPassings, then tourPassingStatus =
              // FINISHED
            })
        .filter(aBoolean -> aBoolean)
        .count();
  }

  private Integer calculateCurrentScore(Tour tour, UUID userId) {
    final List<Integer> weights = tour.getTourTasks().stream().map(TourTask::getWeight).toList();
    final List<Integer> scores =
        tour.getTourTasks().stream()
            .map(
                tourTask -> {
                  // if for one Task there will be a few TaskPassings than take highest score
                  final List<TaskPassing> taskPassings =
                      taskPassingRepository.findAllByTaskIdAndUserKeycloakId(
                          tourTask.getTask().getId(), userId);
                  final Optional<TaskPassing> taskPassingOptional =
                      taskPassings.stream().max(Comparator.comparing(TaskPassing::getScore));
                  final TaskPassing taskPassing =
                      taskPassingOptional.orElse(TaskPassing.builder().score(0).build());
                  return taskPassing.getScore();
                })
            .toList();

    int sumScoresWeights = 0;
    int sumWeights = 0;
    for (int i = 0; i < scores.size(); i++) {
      sumScoresWeights += scores.get(i) * weights.get(i);
      sumWeights += weights.get(i);
    }
    if (sumWeights == 0) {
      return 0;
    }
    final float score = (float) sumScoresWeights / sumWeights;
    return Math.round(score);
  }

  private TourPassing findTourPassingByTourIdAndUserId(UUID tourId, UUID userId) {
    return tourPassingRepository
        .findByTourIdAndKeycloakUserId(tourId, userId)
        .orElseThrow(() -> new TourPassingNotFoundException(tourId, userId));
  }

  private void validateOneTourPassingForUser(UUID tourId, UUID userId) {
    final boolean userIsPassingTour =
        tourPassingRepository.findByTourIdAndKeycloakUserId(tourId, userId).isPresent();
    if (userIsPassingTour) {
      throw new UserHasActiveTourPassingException(tourId, userId);
    }
  }
}
