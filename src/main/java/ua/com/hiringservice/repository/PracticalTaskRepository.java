package ua.com.hiringservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hiringservice.model.entity.task.PracticalTask;

/**
 * Repository interface for managing PracticalTask entities.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface PracticalTaskRepository extends JpaRepository<PracticalTask, UUID> {}
