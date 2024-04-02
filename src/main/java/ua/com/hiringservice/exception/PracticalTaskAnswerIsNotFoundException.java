package ua.com.hiringservice.exception;

import java.util.UUID;

/** Exception thrown when a Practical Task Answer with a specific ID is not found. */
public class PracticalTaskAnswerIsNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Practical Task Answer with id [%s] is not found.";

  public PracticalTaskAnswerIsNotFoundException(UUID practicalTaskAnswerId) {
    super(MESSAGE.formatted(practicalTaskAnswerId));
  }
}
