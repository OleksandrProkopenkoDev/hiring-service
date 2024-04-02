package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception thrown when attempting to add a practical task to a quiz task, which is not allowed.
 */
public class CannotAddPracticalTaskToQuizException extends RuntimeException {

  private static final String MESSAGE =
      "Trying to add PracticalTask  with id [%s] to Task of type QUIZ with id [%s], what is not"
          + " allowed.";

  public CannotAddPracticalTaskToQuizException(UUID taskId, UUID practicalTaskId) {
    super(MESSAGE.formatted(practicalTaskId, taskId));
  }
}
