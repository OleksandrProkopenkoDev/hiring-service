package ua.com.hiringservice.exception;

import java.util.UUID;
import ua.com.hiringservice.model.entity.task.Task;

/**
 * This exception is thrown when attempting to associate a task question with a task to which it
 * does not belong. It provides information about the mismatched task and task question identifiers.
 */
public class TaskQuestionBelongsToOtherTaskException extends RuntimeException {

  private static final String ERROR_MESSAGE =
      "TaskQuestion entity with id: [%s] not belongs to Task with id [%s].";

  public TaskQuestionBelongsToOtherTaskException(Task task, UUID taskQuestionId) {
    super(ERROR_MESSAGE.formatted(taskQuestionId, task.getId()));
  }
}
