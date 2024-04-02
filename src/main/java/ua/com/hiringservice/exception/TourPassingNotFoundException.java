package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception thrown when a tour passing is not found for a specific tour and user. This exception
 * indicates that no tour passing record exists for the provided tour ID and user ID.
 */
public class TourPassingNotFoundException extends RuntimeException {
  private static final String MESSAGE =
      "Tour passing not found for Tour with id [%s] and user with id [%s]";

  public TourPassingNotFoundException(UUID tourId, UUID userId) {
    super(MESSAGE.formatted(tourId, userId));
  }
}
