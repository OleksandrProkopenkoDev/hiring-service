package ua.com.hiringservice.service.task;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.dto.task.QuizPassingResponseDto;

/**
 * Service interface for managing quiz passing operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface QuizPassingService {

  void startQuizPassing(UUID taskId);

  AnswerDto getAnswerByQuestion(UUID taskId, Integer indexInTask);

  void finishQuizPassing(UUID taskId);

  Page<QuizPassingResponseDto> getAssessedDoneTaskPassings(Pageable pageable);

  Page<QuizPassingResponseDto> getAssessedDoneQuizPassingByTaskId(
      UUID userId, UUID taskId, Pageable pageable);
}
