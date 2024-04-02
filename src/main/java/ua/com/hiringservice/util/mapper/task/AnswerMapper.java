package ua.com.hiringservice.util.mapper.task;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.exception.ContentUpdateTypeException;
import ua.com.hiringservice.model.content.AutoAssessable;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.enums.exam.QuestionType;

/** Replace this stub by correct Javadoc. */
@Mapper(config = MapperConfig.class)
public interface AnswerMapper {

  Answer toEntity(AnswerDto answerDto);

  @Named("toDto")
  AnswerDto toDto(Answer answer);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "content", expression = "java(updateContent(answer, answerDto))")
  void updateEntityFromDto(@MappingTarget Answer answer, AnswerDto answerDto);

  @Mapping(target = "content", expression = "java(mapContent(answer))")
  AnswerDto toUserDto(Answer answer);

  default Content mapContent(final Answer answer) {
    final Content content = answer.getContent();

    if (content instanceof AutoAssessable) {
      ((AutoAssessable) content).hideCorrectAnswer();
    }

    content.excludeHtmlFromTextImageWrapper();

    return content;
  }

  default Content updateContent(final Answer answer, final AnswerDto answerDto) {
    final Content targetContent = answer.getContent();
    final Content sourceContent = answerDto.getContent();
    final QuestionType targetType = targetContent.getQuestionType();
    final QuestionType sourceType = sourceContent.getQuestionType();

    if (!targetType.equals(sourceType)) {
      throw new ContentUpdateTypeException(targetType, sourceType);
    }

    return targetContent.getSafeUpdatedContent(sourceContent);
  }
}
