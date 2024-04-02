package ua.com.hiringservice.exception;

import static java.lang.String.format;

/**
 * Exception thrown when the size of the list of task questions provided for an update operation
 * does not match the existing list size in the database.
 */
public class TaskQuestionsListSizeException extends RuntimeException {

  public static final String MESSAGE =
      "TaskQuestions List size passed to update [%s]  not equals existing in Database list size"
          + " [%s]";

  public TaskQuestionsListSizeException(int entityListSize, int dtoListSize) {
    super(format(MESSAGE, dtoListSize, entityListSize));
  }
}
