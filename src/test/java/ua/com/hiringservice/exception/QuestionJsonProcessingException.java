package ua.com.hiringservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

/** Exception thrown when there is a failure in processing JSON for a question. */
public class QuestionJsonProcessingException extends RuntimeException {
  public QuestionJsonProcessingException(JsonProcessingException e) {
    super("Failed to process Json for question", e);
  }
}
