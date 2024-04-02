package ua.com.hiringservice.exception;

import java.util.List;

/**
 * Exception thrown when a list of TaskQuestions contains duplicates in the sequence. This exception
 * indicates that the sequence values within the list must be unique.
 */
public class TaskQuestionIndexInTaskDuplicateException extends RuntimeException {
  public TaskQuestionIndexInTaskDuplicateException(List<Integer> sequenceList) {
    super("List of TaskQuestions contains duplicates in sequence : " + sequenceList);
  }
}
