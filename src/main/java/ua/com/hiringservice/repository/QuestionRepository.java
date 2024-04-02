package ua.com.hiringservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.com.hiringservice.model.entity.task.Question;

/**
 * JPA repository for handling operations related to {@link Question} entities. Extends {@link
 * JpaRepository} and {@link JpaSpecificationExecutor}. The repository is designed to work with
 * entities of type {@link Question} and uses {@link UUID} as the primary key type.
 */
@Repository
public interface QuestionRepository
    extends JpaRepository<Question, UUID>, JpaSpecificationExecutor<Question> {}
