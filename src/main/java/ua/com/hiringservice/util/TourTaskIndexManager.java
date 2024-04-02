package ua.com.hiringservice.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import ua.com.hiringservice.model.entity.tour.Tour;
import ua.com.hiringservice.model.entity.tour.TourTask;

/**
 * The TourTaskIndexManager class provides utility methods for managing indexes of tour tasks within
 * a Tour. It offers functionality to retrieve the next available index and organize the indexes of
 * existing tour tasks (delete the gaps if tourTask was deleted).
 */
public final class TourTaskIndexManager {

  private TourTaskIndexManager() {
    throw new UnsupportedOperationException(
        "Utility TourTaskIndexManager class cannot be instantiated");
  }

  public static Integer getNextIndex(Tour tour) {
    organizeIndexesIn(tour);
    return tour.getTourTasks().size() + 1;
  }

  public static void organizeIndexesIn(Tour tour) {
    final List<TourTask> tourTasks =
        tour.getTourTasks().stream()
            .sorted(Comparator.comparingInt(TourTask::getIndexInTour))
            .toList();

    // Create a map to store the original indexes of the tour questions
    final Map<UUID, Integer> originalIndexes =
        tourTasks.stream()
            .collect(
                Collectors.toMap(TourTask::getId, tourTask -> tourTasks.indexOf(tourTask) + 1));

    // Update the indexInTask values
    tourTasks.forEach(tourTask -> tourTask.setIndexInTour(originalIndexes.get(tourTask.getId())));
  }
}
