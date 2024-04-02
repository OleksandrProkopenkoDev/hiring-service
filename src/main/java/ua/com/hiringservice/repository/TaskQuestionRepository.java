package ua.com.hiringservice.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hiringservice.model.entity.task.TaskQuestion;

/** Repository interface for accessing and managing QuizQuestion entities. */
public interface TaskQuestionRepository extends JpaRepository<TaskQuestion, UUID> {

  List<TaskQuestion> findAllByQuestionId(UUID questionId);
}
