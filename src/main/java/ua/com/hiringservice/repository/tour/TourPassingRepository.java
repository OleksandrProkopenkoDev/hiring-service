package ua.com.hiringservice.repository.tour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hiringservice.model.entity.tour.TourPassing;
import ua.com.hiringservice.model.enums.tour.TourStatus;

/**
 * Repository interface for managing {@link TourPassing} entities. This interface extends {@link
 * JpaRepository} to provide CRUD (Create, Read, Update, Delete) operations for interacting with the
 * database table storing tour passing records.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-31
 */
public interface TourPassingRepository extends JpaRepository<TourPassing, UUID> {

  Optional<TourPassing> findByKeycloakUserIdAndTourId(UUID currentUserKeycloakId, UUID tourId);

  Optional<TourPassing> findByTourIdAndKeycloakUserId(UUID tourId, UUID userId);

  List<TourPassing> findAllByTourIdAndStatus(UUID tourId, TourStatus tourStatus);
}
