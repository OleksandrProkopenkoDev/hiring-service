package ua.com.hiringservice.exception.tour;

import java.util.UUID;

/** Exception thrown when a tour with the specified ID is not found. */
public class TourNotFoundException extends RuntimeException {
  private static final String ERROR_MESSAGE = "Tour with id: [%s] not found.";

  public TourNotFoundException(UUID tourId) {
    super(ERROR_MESSAGE.formatted(tourId));
  }
}
