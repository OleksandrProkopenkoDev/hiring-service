package ua.com.hiringservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hiringservice.model.entity.task.PracticalTaskAnswer;

/**
 * Repository interface for managing user practical task answer entities.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface PracticalTaskAnswerRepository extends JpaRepository<PracticalTaskAnswer, UUID> {}
