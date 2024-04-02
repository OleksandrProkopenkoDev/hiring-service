package ua.com.hiringservice.service.task.impl;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.exception.AnswerAlreadyProvidedException;
import ua.com.hiringservice.exception.AnswerNotFoundException;
import ua.com.hiringservice.exception.AnswerProvideException;
import ua.com.hiringservice.model.content.AutoAssessable;
import ua.com.hiringservice.model.dto.filter.AnswerFilterCriteria;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.repository.AnswerRepository;
import ua.com.hiringservice.service.task.AnswerService;
import ua.com.hiringservice.util.QuestionTypeSortUtil;
import ua.com.hiringservice.util.builder.AnswerSpecificationBuilder;
import ua.com.hiringservice.util.mapper.task.AnswerMapper;

/**
 * Implementation of Answer Service.
 *
 * @author Zakhar Kuropiatnyk
 */
@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

  private final AnswerRepository answerRepository;
  private final AnswerMapper answerMapper;

  @Override
  public AnswerDto findById(final UUID id) {
    return answerMapper.toDto(findAnswerById(id));
  }

  @Override
  public AnswerDto create(final AnswerDto answerDto) {
    return answerMapper.toDto(answerRepository.save(answerMapper.toEntity(answerDto)));
  }

  @Override
  public void deleteAnswerById(final UUID id) {
    answerRepository.deleteById(id);
  }

  @Override
  public Page<AnswerDto> findAll(final AnswerFilterCriteria filter, final Pageable pageable) {

    final Specification<Answer> specification = getSpecificationByAnswerFilter(filter);

    if (QuestionTypeSortUtil.isSortByQuestionType(pageable)) {

      return getSortedByQuestionType(pageable, specification);
    }

    return answerRepository
        .findAll(getSpecificationByAnswerFilter(filter), pageable)
        .map(answerMapper::toDto);
  }

  @Override
  public AnswerDto provide(final UUID id, final AnswerDto answerDto) {

    final Answer answer = findAnswerById(id);
    validateProvidePossibility(answer, answerDto);
    final AnswerDto updatedAnswer = updateAnswerFromDto(answer, answerDto);

    if (updatedAnswer.getContent() instanceof final AutoAssessable autoAssessable) {
      autoAssessable.imagesToTextProvidedAnswer();
    }

    updatedAnswer.setStatus(PassingStatus.ANSWERED);
    return updateAnswerFromDto(answer, updatedAnswer);
  }

  private Answer findAnswerById(final UUID id) {
    return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
  }

  private Specification<Answer> getSpecificationByAnswerFilter(final AnswerFilterCriteria filter) {
    if (filter == null) {
      return new AnswerSpecificationBuilder().build();
    }
    return new AnswerSpecificationBuilder()
        .questionType(filter.getQuestionType())
        .minDuration(filter.getMinDuration())
        .maxDuration(filter.getMaxDuration())
        .status(filter.getStatus())
        .quizPassing(filter.getQuizPassingIds())
        .build();
  }

  private void validateProvidePossibility(Answer answer, AnswerDto answerDto) {
    if (answer.getStatus().equals(PassingStatus.ANSWERED)
        || answer.getStatus().equals(PassingStatus.GRADED)) {
      throw new AnswerAlreadyProvidedException(answer.getId());
    }
    if (!answer.getIndexInTask().equals(answerDto.getIndexInTask())) {
      throw new AnswerProvideException(
          answer.getId(),
          answer.getIndexInTask(),
          answerDto.getIndexInTask(),
          AnswerProvideException.INDEX_IN_TASK);
    }
    if (!answer.getDuration().equals(answerDto.getDuration())) {
      throw new AnswerProvideException(
          answer.getId(),
          answer.getDuration(),
          answerDto.getDuration(),
          AnswerProvideException.DURATION);
    }
  }

  private AnswerDto updateAnswerFromDto(final Answer answer, final AnswerDto answerDto) {

    answerMapper.updateEntityFromDto(answer, answerDto);

    return answerMapper.toDto(answerRepository.save(answer));
  }

  private Page<AnswerDto> getSortedByQuestionType(
      final Pageable pageable, final Specification<Answer> specification) {
    final Specification<Answer> specificationWithSort =
        specification.and(QuestionTypeSortUtil.getQuestionTypeSpecificationSort(pageable));
    final Pageable pageableForQuestionTypeSort =
        QuestionTypeSortUtil.getPageableSortByQuestionType(pageable);
    final Page<Answer> page =
        answerRepository.findAll(specificationWithSort, pageableForQuestionTypeSort);
    return QuestionTypeSortUtil.getRequestPageSortedByQuestionType(page).map(answerMapper::toDto);
  }
}
