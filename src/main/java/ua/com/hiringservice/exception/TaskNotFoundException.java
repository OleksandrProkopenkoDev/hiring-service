package ua.com.hiringservice.exception;

import java.util.UUID;

/** Exception thrown when a task entity with a specific ID is not found. */
public class TaskNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Task entity with id: %s not found.";

  public TaskNotFoundException(UUID taskId) {
    super(ERROR_MESSAGE.formatted(taskId));
  }
}
