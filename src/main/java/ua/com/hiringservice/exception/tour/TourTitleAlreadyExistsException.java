package ua.com.hiringservice.exception.tour;

/**
 * Exception thrown when attempting to create a tour with a title that already exists. This
 * exception indicates that a tour with the same title already exists in the system.
 */
public class TourTitleAlreadyExistsException extends RuntimeException {
  private static final String ERROR_MESSAGE = "Tour with title: [%s] already exists.";

  public TourTitleAlreadyExistsException(String tourTitle) {
    super(ERROR_MESSAGE.formatted(tourTitle));
  }
}
