package ua.com.hiringservice.service.task.impl;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.hiringservice.exception.ChangeTypeInQuestionException;
import ua.com.hiringservice.exception.QuestionAlreadyUsesInTaskQuestion;
import ua.com.hiringservice.exception.QuestionNotFoundException;
import ua.com.hiringservice.model.dto.filter.QuestionFilterCriteria;
import ua.com.hiringservice.model.dto.task.QuestionDto;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.repository.QuestionRepository;
import ua.com.hiringservice.repository.TaskQuestionRepository;
import ua.com.hiringservice.service.task.ImageService;
import ua.com.hiringservice.service.task.QuestionService;
import ua.com.hiringservice.util.QuestionTypeSortUtil;
import ua.com.hiringservice.util.builder.QuestionSpecificationBuilder;
import ua.com.hiringservice.util.mapper.task.QuestionMapper;

/**
 * Implementation of the {@link QuestionService} interface providing CRUD operations for {@link
 * Question} entities. Uses the {@link QuestionRepository} for data access and the {@link
 * QuestionMapper} for mapping between DTOs and entities.
 */
@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;
  private final ImageService imageService;
  private final ImageGeneratorServiceImpl imageGeneratorService;
  private final TaskQuestionRepository taskQuestionRepository;

  @Override
  public QuestionDto findById(final UUID id) {
    final Question question = findQuestionById(id);

    return questionMapper.toDto(question);
  }

  @Override
  public Page<QuestionDto> findAllByFilterCriteria(
      Pageable pageable, final QuestionFilterCriteria filterCriteria) {

    final Specification<Question> specification = getSpecification(filterCriteria);

    if (QuestionTypeSortUtil.isSortByQuestionType(pageable)) {

      return getSortedByQuestionType(pageable, specification);
    }
    return questionRepository.findAll(specification, pageable).map(questionMapper::toDto);
  }

  @Override
  @Transactional
  public QuestionDto save(final QuestionDto questionDto) {

    addImagesToContent(questionDto);

    final Question question = questionMapper.toEntity(questionDto);

    return questionMapper.toDto(questionRepository.save(question));
  }

  @Override
  @Transactional
  public QuestionDto update(final UUID questionId, final QuestionDto questionDto) {
    final Question question = findQuestionById(questionId);

    if (!question
        .getContent()
        .getQuestionType()
        .equals(questionDto.getContent().getQuestionType())) {
      throw new ChangeTypeInQuestionException(questionId);
    }

    addImagesToContent(questionDto);
    questionMapper.updateEntityFromDto(question, questionDto);

    questionRepository.save(question);

    return questionMapper.toDto(question);
  }

  @Override
  public void deleteById(final UUID id) {
    if (!taskQuestionRepository.findAllByQuestionId(id).isEmpty()) {
      throw new QuestionAlreadyUsesInTaskQuestion(id);
    }

    questionRepository.deleteById(id);
  }

  private Question findQuestionById(final UUID id) {
    return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
  }

  private Specification<Question> getSpecification(final QuestionFilterCriteria filterCriteria) {
    return new QuestionSpecificationBuilder()
        .questionType(filterCriteria.getQuestionType())
        .maxDuration(filterCriteria.getMaxDuration())
        .minDuration(filterCriteria.getMinDuration())
        .build();
  }

  private void addImagesToContent(final QuestionDto questionDto) {
    questionDto
        .getContent()
        .collectAllTextImageWrappersWithImageNull()
        .forEach(
            wrapper -> {
              final byte[] imageBody;
              imageBody = imageGeneratorService.convertHTMLContentToImageBytes(wrapper.getHtml());
              final String imageId = imageService.saveImage(imageBody).getId();
              wrapper.setImage(imageId);
            });
  }

  private Page<QuestionDto> getSortedByQuestionType(
      final Pageable pageable, final Specification<Question> specification) {

    final Specification<Question> specificationWithSort =
        specification.and(QuestionTypeSortUtil.getQuestionTypeSpecificationSort(pageable));

    final Pageable pageableForQuestionTypeSort =
        QuestionTypeSortUtil.getPageableSortByQuestionType(pageable);

    final Page<Question> page =
        questionRepository.findAll(specificationWithSort, pageableForQuestionTypeSort);

    return QuestionTypeSortUtil.getRequestPageSortedByQuestionType(page).map(questionMapper::toDto);
  }
}
