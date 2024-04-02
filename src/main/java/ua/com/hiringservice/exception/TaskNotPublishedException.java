package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception thrown when an attempt is made to create a task passing for a task that is not
 * published.
 */
public class TaskNotPublishedException extends RuntimeException {

  private static final String ERROR_MESSAGE =
      "Task passing cannot be created because task %s is not published";

  public TaskNotPublishedException(final UUID taskId) {
    super(ERROR_MESSAGE.formatted(taskId));
  }
}
