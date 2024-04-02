package ua.com.hiringservice.util.validations;

import static ua.com.hiringservice.util.validations.TaskValidation.throwIfPassingScoreIsGreaterThanMaxScore;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfQuestionIdIsNull;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfQuestionNotFound;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfSequenceIsInvalid;

import java.util.List;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.repository.QuestionRepository;

/** Utility class for validating Question entities. */
public final class QuestionValidation {

  private QuestionValidation() {
    throw new UnsupportedOperationException(
        "Utility QuestionValidation class cannot be instantiated");
  }

  public static void validateTaskDto(TaskDto taskDto, QuestionRepository questionRepository) {
    final List<TaskQuestionDto> taskQuestionDtos = taskDto.getTaskQuestionDtos();
    throwIfPassingScoreIsGreaterThanMaxScore(taskDto);

    throwIfQuestionIdIsNull(taskQuestionDtos);

    throwIfQuestionNotFound(taskQuestionDtos, questionRepository);

    throwIfSequenceIsInvalid(taskQuestionDtos);
  }
}
