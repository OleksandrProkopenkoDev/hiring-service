package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception which should be thrown when TaskQuestion entity not found in Database.
 *
 * @author Oleksandr Prokopenko
 */
public class TaskQuestionNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE = "TaskQuestion entity with id: %s not found.";

  public TaskQuestionNotFoundException(UUID questionId) {
    super(ERROR_MESSAGE.formatted(questionId));
  }
}
