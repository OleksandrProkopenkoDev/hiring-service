package ua.com.hiringservice.util.validations;

import static ua.com.hiringservice.util.validations.TaskValidation.throwIfSequenceIsInvalid;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfTaskQuestionIdIsNull;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfTaskQuestionListSizeNotValid;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfTaskQuestionNotFound;

import java.util.List;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.repository.TaskQuestionRepository;

/** Utility class for validating TaskQuestion entities. */
public final class TaskQuestionValidation {

  private TaskQuestionValidation() {
    throw new UnsupportedOperationException(
        "Utility TaskQuestionValidation class cannot be instantiated");
  }

  public static void validateTaskQuestions(
      TaskDto taskDto, Task task, TaskQuestionRepository taskQuestionRepository) {
    final List<TaskQuestionDto> taskQuestionDtos = taskDto.getTaskQuestionDtos();

    throwIfTaskQuestionIdIsNull(taskQuestionDtos);

    throwIfTaskQuestionNotFound(taskQuestionDtos, taskQuestionRepository);

    throwIfTaskQuestionListSizeNotValid(task, taskQuestionDtos);

    throwIfSequenceIsInvalid(taskQuestionDtos);
  }
}
