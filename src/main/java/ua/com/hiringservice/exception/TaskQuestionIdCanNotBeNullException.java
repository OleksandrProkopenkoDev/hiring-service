package ua.com.hiringservice.exception;

/**
 * Exception thrown when a TaskQuestion lacks a required ID, it may happen during update request,
 * what have a body without required TaskQuestion.id This exception indicates that task questions
 * must have an ID.
 */
public class TaskQuestionIdCanNotBeNullException extends RuntimeException {
  private static final String ERROR_MESSAGE = "Task questions must have an ID";

  public TaskQuestionIdCanNotBeNullException() {
    super(ERROR_MESSAGE);
  }
}
