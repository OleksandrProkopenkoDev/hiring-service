package ua.com.hiringservice.exception;

import java.util.UUID;

/** Exception thrown when a practical task with a specific ID is not found. */
public class PracticalTaskNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Practical task with id [%s] is not found.";

  public PracticalTaskNotFoundException(UUID practicalTaskId) {
    super(MESSAGE.formatted(practicalTaskId));
  }
}
