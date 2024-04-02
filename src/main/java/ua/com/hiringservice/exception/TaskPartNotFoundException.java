package ua.com.hiringservice.exception;

/** Replace this stub by correct Javadoc. */
public class TaskPartNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE =
      "Task part with id: %s in task with id: %s not found.";

  public TaskPartNotFoundException(Long taskPartId, Long taskId) {
    super(ERROR_MESSAGE.formatted(taskPartId, taskId));
  }
}
