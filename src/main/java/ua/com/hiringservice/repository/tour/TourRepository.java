package ua.com.hiringservice.repository.tour;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.hiringservice.model.entity.tour.Tour;

/** Replace this stub by correct Javadoc. */
@Repository
public interface TourRepository extends JpaRepository<Tour, UUID> {
  Optional<Tour> findByTitle(String title);
}
