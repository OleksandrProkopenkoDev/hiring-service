package ua.com.hiringservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.testsupport.constant.TestDataConstant.QUESTIONS_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_QUESTIONS_DATA_SQL;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateExistingTask1Id;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateExistingTask3Id;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask1DtoAfterDelete1Qq;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.generateExistingTask1Question1Id;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.generateExistingTask2Question1Id;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.generateExistingTask3Question1Id;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.generateTaskQuestion1Dto;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.generateTaskQuestion1DtoUpdated;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.generateTaskQuestion1DtoWithSequence1;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.generateTaskQuestion4Dto;

import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ua.com.hiringservice.IntegrationTestBase;
import ua.com.hiringservice.exception.TaskAlreadyContainQuestionException;
import ua.com.hiringservice.exception.TaskAlreadyPublishedException;
import ua.com.hiringservice.exception.TaskQuestionBelongsToOtherTaskException;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.service.task.TaskQuestionService;
import ua.com.hiringservice.service.task.impl.TaskQuestionServiceImpl;

/**
 * JUnit test class for {@link TaskQuestionServiceImpl}.
 *
 * <p>This class contains test cases for various methods in the {@link TaskQuestionServiceImpl}
 * implementation of the {@link TaskQuestionService} interface. It covers scenarios such as adding
 * task questions to taskzes, handling exceptions when questions are already added or have duplicate
 * sequences, and deleting task questions from taskzes.
 *
 * <p>Integration tests are performed, and the test data is initialized using SQL scripts. Mocked
 * data generators and predefined constants are used for consistent and repeatable test scenarios.
 *
 * <p>Note: The actual implementation of the TaskQuestionServiceImpl and its dependencies is
 * expected to be properly configured in the Spring context for successful execution of these tests.
 *
 * @author Oleksandr Prokopenko
 * @version 1.0
 * @since 2024-01-12
 */
@Sql(scripts = {QUESTIONS_DATA_SQL, TASK_DATA_SQL, TASK_QUESTIONS_DATA_SQL})
class TaskQuestionServiceImplIntegrationTest extends IntegrationTestBase {

  public static final String NOT_ALLOWED_BUT_WAS_UPDATED =
      "Task is published, update taskQuestions not allowed but was updated";
  @Autowired private TaskQuestionService taskQuestionService;

  @Test
  void addTaskQuestionToTask_shouldAdd_whenTaskQuestionIsValid() {
    final UUID task1Id = generateExistingTask1Id();

    final TaskQuestionDto taskQuestionDto = generateTaskQuestion4Dto();

    final TaskDto taskDto = taskQuestionService.addTaskQuestionToTask(task1Id, taskQuestionDto);

    assertThat(taskDto.getTaskQuestionDtos()).size().isEqualTo(4);
  }

  @Test
  void addTaskQuestionToTask_shouldThrow_whenQuestionAlreadyAdded() {
    final UUID task1Id = generateExistingTask1Id();

    final TaskQuestionDto taskQuestionDto = generateTaskQuestion1Dto();

    assertThrows(
        TaskAlreadyContainQuestionException.class,
        () -> taskQuestionService.addTaskQuestionToTask(task1Id, taskQuestionDto));
  }

  @Test
  void addTaskQuestionToTask_shouldThrow_whenQuestionSequenceNotUnique() {
    final UUID task1Id = generateExistingTask1Id();

    final TaskQuestionDto taskQuestionDto = generateTaskQuestion1DtoWithSequence1();

    assertThrows(
        TaskAlreadyContainQuestionException.class,
        () -> taskQuestionService.addTaskQuestionToTask(task1Id, taskQuestionDto));
  }

  @Test
  void deleteTaskQuestionFromTask_shouldDelete_whenIdIsValid() {
    final UUID task1Id = generateExistingTask1Id();
    final UUID task1Question1Id = generateExistingTask1Question1Id();

    final TaskDto taskDto =
        taskQuestionService.deleteTaskQuestionFromTask(task1Id, task1Question1Id);

    final int expectedTaskQuestionDtosSize = 2;
    assertThat(taskDto.getTaskQuestionDtos()).size().isEqualTo(expectedTaskQuestionDtosSize);
  }

  @Test
  void updateTaskQuestion_shouldIgnorePassedIndexInTask() {
    final UUID taskId = generateExistingTask1Id();
    final UUID taskQuestionId = generateExistingTask1Question1Id();
    final TaskQuestionDto taskQuestionDto = generateTaskQuestion1DtoUpdated();
    final Integer updatedIndexInTask = taskQuestionDto.getIndexInTask();

    final TaskDto taskDto =
        taskQuestionService.updateTaskQuestion(taskId, taskQuestionId, taskQuestionDto);

    assertFalse(
        taskDto.getTaskQuestionDtos().stream()
            .anyMatch(qqDto -> Objects.equals(qqDto.getIndexInTask(), updatedIndexInTask)),
        "IndexInTask from passed taskQuestionDto should be ignored, but was used");
  }

  @Test
  void updateTaskQuestion_shouldIgnorePassedQuestion() {
    final UUID taskId = generateExistingTask1Id();
    final UUID taskQuestionId = generateExistingTask1Question1Id();
    final TaskQuestionDto taskQuestionDtoActual = generateTaskQuestion1Dto();
    final TaskQuestionDto taskQuestionDtoUpdated = generateTaskQuestion1DtoUpdated();

    final TaskDto taskDto =
        taskQuestionService.updateTaskQuestion(taskId, taskQuestionId, taskQuestionDtoUpdated);

    taskDto
        .getTaskQuestionDtos()
        .forEach(
            taskQuestionDto -> {
              if (taskQuestionDto.getId().equals(taskQuestionId)) {
                assertNotEquals(taskQuestionDtoActual, taskQuestionDto);
              }
            });
  }

  @Test
  void deleteTaskQuestionFromTask_shouldUpdateIndexesInTask() {
    final UUID taskId = generateExistingTask1Id();
    final UUID taskQuestionId = generateExistingTask1Question1Id();
    final TaskDto taskDtoExpected = generateTask1DtoAfterDelete1Qq();

    final TaskDto taskDtoActual =
        taskQuestionService.deleteTaskQuestionFromTask(taskId, taskQuestionId);

    assertThat(taskDtoActual)
        .as("IndexesInTask are not updated properly")
        .usingRecursiveComparison()
        .ignoringFields("updatedAt")
        .isEqualTo(taskDtoExpected);
  }

  @Test
  void deleteTaskQuestionFromTask_shouldThrow_whenTaskQuestionBelongsToOtherTask() {
    final UUID taskId = generateExistingTask1Id();
    final UUID taskQuestionId = generateExistingTask2Question1Id();

    assertThrows(
        TaskQuestionBelongsToOtherTaskException.class,
        () -> taskQuestionService.deleteTaskQuestionFromTask(taskId, taskQuestionId),
        "Trying to delete this taskQuestion from other task make no sense, but was deleted");
  }

  @Test
  void deleteTaskQuestionFromTask_shouldThrow_whenTaskIsPublished() {
    final UUID taskId = generateExistingTask3Id();
    final UUID taskQuestionId = generateExistingTask3Question1Id();

    assertThrows(
        TaskAlreadyPublishedException.class,
        () -> taskQuestionService.deleteTaskQuestionFromTask(taskId, taskQuestionId),
        NOT_ALLOWED_BUT_WAS_UPDATED);
  }

  @Test
  void addTaskQuestionToTask_shouldThrow_whenTaskIsPublished() {
    final UUID taskId = generateExistingTask3Id();
    final TaskQuestionDto taskQuestionDto = generateTaskQuestion1Dto();

    assertThrows(
        TaskAlreadyPublishedException.class,
        () -> taskQuestionService.addTaskQuestionToTask(taskId, taskQuestionDto),
        NOT_ALLOWED_BUT_WAS_UPDATED);
  }

  @Test
  void updateTaskQuestion_shouldThrow_whenTaskIsPublished() {
    final UUID taskId = generateExistingTask3Id();
    final UUID taskQuestionId = generateExistingTask3Question1Id();
    final TaskQuestionDto taskQuestionDto = generateTaskQuestion1Dto();

    assertThrows(
        TaskAlreadyPublishedException.class,
        () -> taskQuestionService.updateTaskQuestion(taskId, taskQuestionId, taskQuestionDto),
        NOT_ALLOWED_BUT_WAS_UPDATED);
  }
}
