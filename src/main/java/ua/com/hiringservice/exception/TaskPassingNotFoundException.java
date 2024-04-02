package ua.com.hiringservice.exception;

import java.util.UUID;
import ua.com.hiringservice.model.enums.PassingStatus;

/** Replace this stub by correct Javadoc. */
public class TaskPassingNotFoundException extends RuntimeException {

  private static final String DEFAULT_ERROR_MESSAGE = "Task passing with id %s not found";
  private static final String ERROR_MESSAGE_WITH_USER_AND_STATUS =
      "Task passing for user with id %s and from task with id %s with status %s not found";

  public TaskPassingNotFoundException(final UUID taskPassingId) {
    super(DEFAULT_ERROR_MESSAGE.formatted(taskPassingId));
  }

  public TaskPassingNotFoundException(
      final UUID userId, final UUID taskId, final PassingStatus passingStatus) {
    super(ERROR_MESSAGE_WITH_USER_AND_STATUS.formatted(userId, taskId, passingStatus));
  }
}
