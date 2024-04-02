package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception thrown when attempting to add a task question to a task, but the task already contains
 * a question with the given ID.
 */
public class TaskAlreadyContainQuestionException extends RuntimeException {
  public TaskAlreadyContainQuestionException(UUID id) {
    super("Task already contain question with id = " + id);
  }
}
