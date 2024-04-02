package ua.com.hiringservice.service.task.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.exception.IllegalTaskTypeException;
import ua.com.hiringservice.exception.TaskNotPublishedException;
import ua.com.hiringservice.exception.TaskPassingNotFoundException;
import ua.com.hiringservice.exception.UserAlreadyPassingTaskException;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskPassingResponseDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskWithBytesDto;
import ua.com.hiringservice.model.entity.task.PracticalTaskAnswer;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.TaskType;
import ua.com.hiringservice.repository.TaskPassingRepository;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.task.ImageGeneratorService;
import ua.com.hiringservice.service.task.PracticalTaskImageService;
import ua.com.hiringservice.service.task.PracticalTaskPassingService;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.util.mapper.task.TaskPassingMapper;

/**
 * Service implementation for managing practical task passing operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@AllArgsConstructor
@Service
public class PracticalTaskPassingServiceImpl implements PracticalTaskPassingService {

  private final KeycloakService keycloakService;
  private final TaskPassingRepository taskPassingRepository;
  private final ImageGeneratorService imageGeneratorService;
  private final PracticalTaskImageService imageService;
  private final TaskPassingMapper taskPassingMapper;
  private final TaskService taskService;

  @Override
  public void startPracticalTaskPassing(UUID taskId) {
    final UUID userKeycloakId = getUserId();
    final Task task = taskService.findTaskById(taskId);

    validateTask(userKeycloakId, task);

    buildAndSaveTaskPassingObject(userKeycloakId, task);
  }

  @Override
  public PracticalTaskWithBytesDto getPracticalTaskImaged(UUID taskId, Integer width) {
    final UUID userKeycloakId = getUserId();
    final TaskPassing taskPassing = findTaskPassing(userKeycloakId, taskId);
    final byte[] imageBytes =
        imageGeneratorService.convertHTMLContentToImageBytes(
            taskPassing.getTask().getPracticalTask().getTaskText(), width);
    final List<PracticalTaskImageDto> imageDtos = imageService.findAllImagesByIds(taskPassing);

    return PracticalTaskWithBytesDto.builder()
        .practicalTaskAnswerId(taskPassing.getPracticalTaskAnswer().getId())
        .taskTextImage(imageBytes)
        .images(imageDtos)
        .build();
  }

  @Override
  public Page<PracticalTaskPassingResponseDto> getAssessedDonePracticalTaskPassings(
      Pageable pageable) {
    final UUID userKeycloakId = getUserId();
    final Page<TaskPassing> taskPassings =
        taskPassingRepository.findAllByUserKeycloakIdAndStatus(
            userKeycloakId, PassingStatus.GRADED, pageable);
    return mapToPracticalTaskPassingResponseDtoPage(taskPassings);
  }

  @Override
  public Page<PracticalTaskPassingResponseDto> getAssessedDonePracticalTaskPassingsByTaskId(
      UUID userId, UUID taskId, Pageable pageable) {
    final UUID keycloakUserId = Objects.requireNonNullElseGet(userId, this::getUserId);

    final Page<TaskPassing> taskPassings =
        taskPassingRepository.findByUserKeycloakIdAndStatusAndTaskId(
            keycloakUserId, PassingStatus.GRADED, taskId, pageable);

    return mapToPracticalTaskPassingResponseDtoPage(taskPassings);
  }

  @NotNull
  private UUID getUserId() {
    return UUID.fromString(keycloakService.getKeycloakUserInfoFromSecurityContext().getUserId());
  }

  private TaskPassing findTaskPassing(UUID userKeycloakId, UUID taskId) {
    return taskPassingRepository
        .findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            taskId, userKeycloakId, PassingStatus.IN_PROGRESS)
        .orElseThrow(
            () ->
                new TaskPassingNotFoundException(
                    userKeycloakId, taskId, PassingStatus.IN_PROGRESS));
  }

  private void buildAndSaveTaskPassingObject(UUID userKeycloakId, Task task) {
    final TaskPassing taskPassing =
        TaskPassing.builder()
            .task(task)
            .userKeycloakId(userKeycloakId)
            .status(PassingStatus.IN_PROGRESS)
            .build();

    taskPassing.setPracticalTaskAnswer(
        PracticalTaskAnswer.builder()
            .maxScore(taskPassing.getTask().getMaxScore())
            .taskPassing(taskPassing)
            .build());

    taskPassingRepository.save(taskPassing);
  }

  private boolean isUserPassTaskNow(final UUID userKeycloakId) {
    return taskPassingRepository.isUserPassTaskNow(userKeycloakId);
  }

  private Page<PracticalTaskPassingResponseDto> mapToPracticalTaskPassingResponseDtoPage(
      Page<TaskPassing> taskPassings) {
    return taskPassings
        .map(taskPassingMapper::toPracticalTaskPassingResponseDto)
        .filter(
            quizPassingResponseDto ->
                quizPassingResponseDto.getType().equals(TaskType.PRACTICAL_TASK))
        .stream()
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                list ->
                    PageableExecutionUtils.getPage(
                        list, taskPassings.getPageable(), taskPassings::getTotalElements)));
  }

  private void validateTask(UUID userKeycloakId, Task task) {
    if (isUserPassTaskNow(userKeycloakId)) {
      throw new UserAlreadyPassingTaskException(userKeycloakId);
    }

    if (!task.getType().equals(TaskType.PRACTICAL_TASK)) {
      throw new IllegalTaskTypeException(task.getType());
    }

    if (task.getPublished().equals(false)) {
      throw new TaskNotPublishedException(task.getId());
    }
  }
}
