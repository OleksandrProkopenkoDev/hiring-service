package ua.com.hiringservice.exception.tour;

import java.util.UUID;

/**
 * Exception thrown when attempting to add a task to a tour that already contains the task. This
 * exception indicates that the task with the specified ID has already been added to the tour with
 * the specified ID.
 */
public class TaskAlreadyAddedToTourException extends RuntimeException {
  private static final String ERROR_MESSAGE =
      "The Task with id: [%s] has already been added to the Tour with id [%s].";

  public TaskAlreadyAddedToTourException(UUID taskId, UUID tourId) {
    super(ERROR_MESSAGE.formatted(taskId.toString(), tourId.toString()));
  }
}
