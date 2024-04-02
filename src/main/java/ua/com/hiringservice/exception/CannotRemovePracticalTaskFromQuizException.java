package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception thrown when attempting to remove a practical task from a quiz task, which is not
 * possible because the practical task is null.
 */
public class CannotRemovePracticalTaskFromQuizException extends RuntimeException {
  private static final String MESSAGE =
      "Trying to remove PracticalTask  from Task of type QUIZ with id [%s], what is not possible, "
          + "because practicalTask is null.";

  public CannotRemovePracticalTaskFromQuizException(UUID taskId) {
    super(MESSAGE.formatted(taskId));
  }
}
