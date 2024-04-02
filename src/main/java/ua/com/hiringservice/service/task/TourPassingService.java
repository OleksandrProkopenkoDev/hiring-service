package ua.com.hiringservice.service.task;

import java.util.List;
import java.util.UUID;
import ua.com.hiringservice.model.dto.KeycloakUserDto;
import ua.com.hiringservice.model.dto.tour.TourPassingDto;

/**
 * Service interface for managing tour passings. This interface defines methods for starting and
 * updating tour passings. Implementations of this interface handle the logic for interacting with
 * tour passings, including starting and updating them.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-31
 */
public interface TourPassingService {

  TourPassingDto startTourPassing(UUID tourId);

  TourPassingDto startTourPassing(UUID tourId, UUID userId);

  TourPassingDto updateTourPassing(UUID tourId, UUID userId);

  TourPassingDto updateTourPassing(UUID tourId);

  TourPassingDto findTourPassingByTourId(UUID tourId);

  List<KeycloakUserDto> findAllUsersByTourIdFinishedTour(UUID tourId);
}
