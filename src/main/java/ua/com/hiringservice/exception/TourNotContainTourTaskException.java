package ua.com.hiringservice.exception;

import java.util.UUID;

/**
 * Exception thrown when a tour does not contain a tourTask with the specified ID.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-30
 */
public class TourNotContainTourTaskException extends RuntimeException {
  private static final String MESSAGE = "Tour with id [%s] not contain tourTask with id [%s]";

  public TourNotContainTourTaskException(UUID tourId, UUID tourTaskId) {
    super(MESSAGE.formatted(tourId, tourTaskId));
  }
}
