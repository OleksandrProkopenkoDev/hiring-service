package ua.com.hiringservice.service.impl;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ua.com.testsupport.constant.TestDataConstant.QUESTIONS_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_QUESTIONS_DATA_SQL;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateDefaultPageable;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateExistingTask1Id;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateNotExistingTaskId;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask2Dto;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTaskDtoList;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTaskDtoWithoutId;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTaskDtoWithoutIdAndNotExistingQuestionId;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTaskDtoWithoutQuestionId;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import ua.com.hiringservice.IntegrationTestBase;
import ua.com.hiringservice.exception.QuestionNotFoundException;
import ua.com.hiringservice.exception.QuestionWithoutIdException;
import ua.com.hiringservice.exception.TaskNotFoundException;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.service.task.impl.TaskServiceImpl;
import ua.com.testsupport.datagenerator.task.TaskDataGenerator;

/**
 * JUnit test class for {@link TaskServiceImpl}.
 *
 * <p>This class contains test cases for various methods in the {@link TaskServiceImpl}
 * implementation of the {@link TaskService} interface. It covers scenarios such as finding quizzes
 * by ID, filtering quizzes, creating new quizzes, updating existing quizzes, deleting quizzes by
 * ID, and publishing quizzes.
 *
 * <p>Integration tests are performed, and the test data is initialized using SQL scripts. Mocked
 * data generators and predefined constants are used for consistent and repeatable test scenarios.
 *
 * <p>Note: The actual implementation of the QuizServiceImpl and its dependencies is expected to be
 * properly configured in the Spring context for successful execution of these tests.
 *
 * @author Oleksandr Prokopenko
 * @version 1.0
 * @since 2024-01-12
 */
@Sql(scripts = {QUESTIONS_DATA_SQL, TASK_DATA_SQL, TASK_QUESTIONS_DATA_SQL})
class TaskServiceImplIntegrationTest extends IntegrationTestBase {

  @Autowired private TaskService taskService;

  @Test
  void findById_shouldReturnQuiz_whenIdIsValid() {
    final UUID quizId = generateExistingTask1Id();
    final TaskDto expected = TaskDataGenerator.generateTask1Dto();

    final TaskDto actual = taskService.findById(quizId);

    assertEquals(expected, actual, "Returned quiz doesn`t match expected");
  }

  @Test
  void findById_shouldThrow_whenIdNotExists() {
    final UUID notExistingQuizId = generateNotExistingTaskId();
    final TaskNotFoundException exception =
        assertThrows(TaskNotFoundException.class, () -> taskService.findById(notExistingQuizId));
    assertEquals(
        format("Task entity with id: %s not found.", notExistingQuizId),
        exception.getMessage(),
        "Task exists and exception is not thrown");
  }

  @Test
  void findAll_shouldReturnPageWithList() {
    final Page<TaskDto> actualPage = taskService.findAll(generateDefaultPageable());
    final List<TaskDto> actual = actualPage.stream().toList();
    final List<TaskDto> expected = generateTaskDtoList();

    assertTrue(
        expected.containsAll(actual), "Returned page have to include all Dtos from expected");
    assertTrue(expected.size() == actual.size(), "Returned list size have to match expected");
  }

  @Test
  void createQuiz_shouldSaveNewQuiz() {
    final TaskDto taskDto = generateTaskDtoWithoutId();

    final TaskDto actual = taskService.createTask(taskDto);

    assertNotNull(actual.getId(), "QuizId is null after saving to DB");
  }

  @Test
  void createQuiz_shouldThrow_whenQuestionIdNotPresentInRequest() {
    final TaskDto taskDto = generateTaskDtoWithoutQuestionId();

    assertThrows(
        QuestionWithoutIdException.class,
        () -> taskService.createTask(taskDto),
        "QuestionId is present, exception not thrown");
  }

  @Test
  void createQuiz_shouldThrow_whenQuestionIdNotPresentInDatabase() {
    final TaskDto taskDto = generateTaskDtoWithoutIdAndNotExistingQuestionId();

    assertThrows(
        QuestionNotFoundException.class,
        () -> taskService.createTask(taskDto),
        "QuestionId is present, exception not thrown");
  }

  @Test
  void updateQuiz_shouldReturnUpdatedDto_whenQuizIsFound() {
    final UUID quizId = generateExistingTask1Id();
    final TaskDto newTaskDto = generateTask2Dto();

    final TaskDto updatedQuiz = taskService.updateTask(quizId, newTaskDto);

    assertNotNull(updatedQuiz.getId(), "QuizId is null after saving to DB");
    assertThat(updatedQuiz)
        .usingRecursiveComparison()
        .ignoringFields(
            "id",
            "totalDuration",
            "totalWeight",
            "totalQuestion",
            "createdAt",
            "updatedAt",
            "taskQuestionDtos")
        .isEqualTo(newTaskDto);
  }

  @Test
  void deleteQuizById_shouldDelete() {
    final UUID quizId = generateExistingTask1Id();

    taskService.deleteTaskById(quizId);

    assertThrows(
        TaskNotFoundException.class,
        () -> taskService.findById(quizId),
        "Quiz exists after deleting");
  }

  @Test
  void publish_shouldPublishQuiz() {
    final UUID quiz1Id = generateExistingTask1Id();
    final TaskDto publishedQuiz = taskService.publish(quiz1Id);

    assertTrue(publishedQuiz.getPublished(), "Quiz is not published");
  }
}
