package ua.com.hiringservice.exception;

/** Replace this stub by correct Javadoc. */
public class TaskTemplateNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE = "TaskTemplate entity with id: %s not found.";

  public TaskTemplateNotFoundException(Long taskTemplateId) {
    super(ERROR_MESSAGE.formatted(taskTemplateId));
  }
}
