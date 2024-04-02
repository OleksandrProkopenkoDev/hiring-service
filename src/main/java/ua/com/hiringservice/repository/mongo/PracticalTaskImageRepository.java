package ua.com.hiringservice.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;

/**
 * Repository interface for managing PracticalTaskImage entities.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface PracticalTaskImageRepository extends MongoRepository<PracticalTaskImage, String> {}
