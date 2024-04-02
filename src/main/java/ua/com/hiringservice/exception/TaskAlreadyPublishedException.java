package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception thrown when attempting to modify a task that has already been published.
 *
 * @author Oleksandr Prokopenko
 * @version 1.0
 * @since 2024-01-12
 */
public class TaskAlreadyPublishedException extends RuntimeException {

  private static final String ERROR_MESSAGE =
      "Task with id [%s] is already published. You can`t add or delete questions from published"
          + " Task";

  public TaskAlreadyPublishedException(UUID taskId) {
    super(ERROR_MESSAGE.formatted(taskId));
  }
}
