package ua.com.hiringservice.service.task;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.dto.task.AssessPracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskAnswerDto;
import ua.com.hiringservice.model.dto.task.ProvidePracticalTaskAnswerDto;

/**
 * Service interface for managing Practical Task Answers.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface PracticalTaskAnswerService {

  ProvidePracticalTaskAnswerDto provideAnswer(
      UUID practicalTaskAnswerId, ProvidePracticalTaskAnswerDto answerDto);

  AssessPracticalTaskDto assessAnswerManually(
      UUID practicalTaskPassingId, AssessPracticalTaskDto assessDto);

  Page<PracticalTaskAnswerDto> findAll(Pageable pageable);

  PracticalTaskAnswerDto findById(UUID practicalTaskAnswerId);
}
