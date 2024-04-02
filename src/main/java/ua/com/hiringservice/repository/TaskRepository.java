package ua.com.hiringservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.hiringservice.model.entity.task.Task;

/**
 * JPA repository for handling operations related to {@link Task} entities. Extends {@link
 * JpaRepository}. The repository is designed to work with entities of type {@link Task} and uses
 * {@link UUID} as the primary key type.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {}
