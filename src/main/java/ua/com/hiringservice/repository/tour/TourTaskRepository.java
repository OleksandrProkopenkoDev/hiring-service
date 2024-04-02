package ua.com.hiringservice.repository.tour;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hiringservice.model.entity.tour.TourTask;

/**
 * Repository interface for managing {@link TourTask} entities. This interface provides CRUD
 * (Create, Read, Update, Delete) operations for interacting with the database table storing tour
 * tasks.
 */
public interface TourTaskRepository extends JpaRepository<TourTask, UUID> {}
