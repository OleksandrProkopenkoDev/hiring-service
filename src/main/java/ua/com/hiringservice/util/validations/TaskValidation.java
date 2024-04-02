package ua.com.hiringservice.util.validations;

import java.util.List;
import java.util.UUID;
import ua.com.hiringservice.exception.PassingScoreMustBeLessThanMaxScoreException;
import ua.com.hiringservice.exception.QuestionNotFoundException;
import ua.com.hiringservice.exception.QuestionWithoutIdException;
import ua.com.hiringservice.exception.TaskAlreadyContainQuestionException;
import ua.com.hiringservice.exception.TaskAlreadyPublishedException;
import ua.com.hiringservice.exception.TaskQuestionBelongsToOtherTaskException;
import ua.com.hiringservice.exception.TaskQuestionIdCanNotBeNullException;
import ua.com.hiringservice.exception.TaskQuestionIndexInTaskDuplicateException;
import ua.com.hiringservice.exception.TaskQuestionNotFoundException;
import ua.com.hiringservice.exception.TaskQuestionsListSizeException;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskQuestion;
import ua.com.hiringservice.repository.QuestionRepository;
import ua.com.hiringservice.repository.TaskQuestionRepository;

/** Utility class for validating Task and TaskQuestion entities. */
public final class TaskValidation {

  private TaskValidation() {
    throw new UnsupportedOperationException("Utility TaskValidation class cannot be instantiated");
  }

  public static void throwIfQuestionIsNotUnique(Task task, TaskQuestionDto taskQuestionDto) {
    final UUID id = taskQuestionDto.getQuestionDto().getId();
    final boolean questionAlreadyExists =
        task.getTaskQuestions().stream()
            .anyMatch(taskQuestion -> taskQuestion.getQuestion().getId().equals(id));
    if (questionAlreadyExists) {
      throw new TaskAlreadyContainQuestionException(id);
    }
  }

  public static void throwIfSequenceIsInvalid(List<TaskQuestionDto> taskQuestionDtos) {
    final List<Integer> sequenceList =
        taskQuestionDtos.stream().map(TaskQuestionDto::getIndexInTask).toList();
    if (sequenceList.size() != sequenceList.stream().distinct().count()) {
      throw new TaskQuestionIndexInTaskDuplicateException(sequenceList);
    }
  }

  public static void throwIfQuestionNotFound(
      List<TaskQuestionDto> taskQuestionDtos, QuestionRepository questionRepository) {
    taskQuestionDtos.forEach(
        taskQuestionDto -> {
          final UUID id = taskQuestionDto.getQuestionDto().getId();
          if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException(id);
          }
        });
  }

  public static void throwIfQuestionIdIsNull(List<TaskQuestionDto> taskQuestionDtos) {
    final boolean questionIdNotPresent =
        taskQuestionDtos.stream()
            .anyMatch(taskQuestionDto -> taskQuestionDto.getQuestionDto().getId() == null);
    if (questionIdNotPresent) {
      throw new QuestionWithoutIdException();
    }
  }

  public static void throwIfTaskQuestionListSizeNotValid(
      Task task, List<TaskQuestionDto> taskQuestionDtos) {
    // check if number of TaskQuestions in DB equals number of TaskQuestions in passed dto
    final List<TaskQuestion> taskQuestions = task.getTaskQuestions();
    final int entityListSize = taskQuestions.size();
    final int dtoListSize = taskQuestionDtos.size();
    if (entityListSize != dtoListSize) {
      throw new TaskQuestionsListSizeException(entityListSize, dtoListSize);
    }
  }

  public static void throwIfTaskQuestionNotFound(
      List<TaskQuestionDto> taskQuestionDtos, TaskQuestionRepository taskQuestionRepository) {
    taskQuestionDtos.forEach(
        taskQuestionDto -> {
          final UUID id = taskQuestionDto.getId();
          if (!taskQuestionRepository.existsById(id)) {
            throw new TaskQuestionNotFoundException(id);
          }
        });
  }

  public static void throwIfTaskQuestionIdIsNull(List<TaskQuestionDto> taskQuestionDtos) {
    final boolean idIsNull =
        taskQuestionDtos.stream().anyMatch(taskQuestionDto -> taskQuestionDto.getId() == null);
    if (idIsNull) {
      throw new TaskQuestionIdCanNotBeNullException();
    }
  }

  public static void throwIfTaskIsAlreadyPublished(Task taskById) {
    if (taskById.getPublished() != null && taskById.getPublished()) {
      throw new TaskAlreadyPublishedException(taskById.getId());
    }
  }

  public static void throwIfTaskQuestionBelongsToOtherTask(Task task, UUID taskQuestionId) {
    final boolean isPresent =
        task.getTaskQuestions().stream()
            .anyMatch(taskQuestion -> taskQuestion.getId().equals(taskQuestionId));
    if (!isPresent) {
      throw new TaskQuestionBelongsToOtherTaskException(task, taskQuestionId);
    }
  }

  public static void throwIfPassingScoreIsGreaterThanMaxScore(TaskDto taskDto) {
    if (taskDto.getPassingScore() > taskDto.getMaxScore()) {
      throw new PassingScoreMustBeLessThanMaxScoreException(taskDto);
    }
  }
}
