package ua.com.hiringservice.service.task.impl;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.exception.AnswerAlreadyAssessedException;
import ua.com.hiringservice.exception.AnswerAlreadyProvidedException;
import ua.com.hiringservice.exception.AnswerNotFinishedException;
import ua.com.hiringservice.exception.GradeMustBeLessThanMaxScoreException;
import ua.com.hiringservice.exception.PracticalTaskAnswerIsNotFoundException;
import ua.com.hiringservice.model.dto.task.AssessPracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskAnswerDto;
import ua.com.hiringservice.model.dto.task.ProvidePracticalTaskAnswerDto;
import ua.com.hiringservice.model.entity.task.PracticalTaskAnswer;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.repository.PracticalTaskAnswerRepository;
import ua.com.hiringservice.service.task.PracticalTaskAnswerService;
import ua.com.hiringservice.util.mapper.task.PracticalTaskAnswerMapper;

/**
 * Implementation of the {@link PracticalTaskAnswerService} interface for managing Practical Task
 * Answers.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@AllArgsConstructor
@Service
public class PracticalTaskAnswerServiceImpl implements PracticalTaskAnswerService {

  private final PracticalTaskAnswerRepository practicalTaskAnswerRepository;
  private final PracticalTaskAnswerMapper answerMapper;

  @Override
  public ProvidePracticalTaskAnswerDto provideAnswer(
      UUID practicalTaskAnswerId, ProvidePracticalTaskAnswerDto answerDto) {
    final PracticalTaskAnswer answer = findEntityById(practicalTaskAnswerId);
    final PassingStatus status = answer.getTaskPassing().getStatus();
    // 1. validate provide possibility
    if (status.equals(PassingStatus.ANSWERED) || status.equals(PassingStatus.GRADED)) {
      throw new AnswerAlreadyProvidedException(answer.getId());
    }
    // 2. convert dto to entity
    answerMapper.updateEntityFromDto(answer, answerDto);
    // 3. update task passing status, set ANSWERED
    answer.getTaskPassing().setStatus(PassingStatus.ANSWERED);
    // 4. save and return
    final PracticalTaskAnswer saved = practicalTaskAnswerRepository.save(answer);
    return answerMapper.toDto(saved);
  }

  @Override
  public AssessPracticalTaskDto assessAnswerManually(
      UUID practicalTaskPassingId, AssessPracticalTaskDto assessDto) {
    final PracticalTaskAnswer answer = findEntityById(practicalTaskPassingId);

    validateAnswer(practicalTaskPassingId, assessDto, answer);

    answer.getTaskPassing().setScore(assessDto.getScore());
    answer.getTaskPassing().setStatus(PassingStatus.GRADED);

    practicalTaskAnswerRepository.save(answer);
    return assessDto;
  }

  @Override
  public Page<PracticalTaskAnswerDto> findAll(Pageable pageable) {
    return practicalTaskAnswerRepository.findAll(pageable).map(answerMapper::toFullDtoShort);
  }

  @Override
  public PracticalTaskAnswerDto findById(UUID practicalTaskAnswerId) {
    return answerMapper.toFullDtoShort(findEntityById(practicalTaskAnswerId));
  }

  private PracticalTaskAnswer findEntityById(UUID practicalTaskAnswerId) {
    return practicalTaskAnswerRepository
        .findById(practicalTaskAnswerId)
        .orElseThrow(() -> new PracticalTaskAnswerIsNotFoundException(practicalTaskAnswerId));
  }

  private void validateAnswer(
      UUID practicalTaskPassingId, AssessPracticalTaskDto assessDto, PracticalTaskAnswer answer) {

    if (assessDto.getScore() > answer.getMaxScore()) {
      throw new GradeMustBeLessThanMaxScoreException(assessDto.getScore(), answer.getMaxScore());
    }

    final PassingStatus status = answer.getTaskPassing().getStatus();

    if (status.equals(PassingStatus.GRADED)) {
      throw new AnswerAlreadyAssessedException(practicalTaskPassingId);
    }
    if (!status.equals(PassingStatus.ANSWERED)) {
      throw new AnswerNotFinishedException(practicalTaskPassingId);
    }
    if (!assessDto.getComment().isEmpty()) {
      answer.getTaskPassing().getTask().setComment(assessDto.getComment());
    }
  }
}
