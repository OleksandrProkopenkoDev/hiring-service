package ua.com.testsupport.datagenerator.task;

import static org.springdoc.core.utils.Constants.DEFAULT_DESCRIPTION;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.DEFAULT_DURATION;
import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;
import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE_12;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generatePracticalTask;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generatePracticalTaskDto;
import static ua.com.testsupport.datagenerator.task.TaskQuestionDataGenerator.DEFAULT_WEIGHT;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.entity.AuditEntity;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskQuestion;
import ua.com.hiringservice.model.enums.exam.TaskType;

/**
 * The TaskDataGenerator class provides static methods for getting taskDto.
 *
 * @author Artyom Kondratenko
 * @since 12/11/23
 */
public final class TaskDataGenerator {

  private static final UUID TASK1_ID = UUID.fromString("550e8400-1111-41d4-a716-446655440001");
  private static final UUID TASK2_ID = UUID.fromString("550e8400-1111-41d4-a716-446655440002");
  private static final UUID TASK3_ID = UUID.fromString("550e8400-1111-41d4-a716-446655440003");
  private static final UUID TASK4_ID = UUID.fromString("550e8400-1111-41d4-a716-446655440004");

  private static final int DEFAULT_PAGE_SIZE = 5;

  private static final String TASK1_TITLE = "Quiz1 Title";
  private static final String TASK2_TITLE = "Quiz2 Title";
  private static final String TASK3_TITLE = "Quiz3 Title";
  private static final String TASK4_TITLE = "Task4 Title";

  private static final TaskType TYPE_QUIZ = TaskType.QUIZ;
  private static final TaskType TYPE_PRACTICAL_TASK = TaskType.PRACTICAL_TASK;
  private static final String TASK1_DESCRIPTION = "Test your skills";
  private static final String TASK2_DESCRIPTION = "Test your geographical skills";
  private static final String TASK3_DESCRIPTION = "Test your programming skills";
  private static final String TASK4_DESCRIPTION = "algorithm problem";

  private static final Integer TASK1_PASSING_SCORE = 50;
  private static final Integer TASK2_PASSING_SCORE = 60;
  private static final Integer TASK3_PASSING_SCORE = 73;
  private static final Integer TASK4_PASSING_SCORE = 6;
  private static final String TASK1_CREATED_AT = "2024-01-12T00:17:19.699000Z";
  private static final String TASK1_UPDATED_AT = "2024-01-12T00:17:19.699892Z";
  private static final String TASK2_CREATED_AT = "2024-01-12T00:24:06.309000Z";
  private static final String TASK2_UPDATED_AT = "2024-01-12T00:24:06.309765Z";

  private static final String TASK3_CREATED_AT = "2024-01-12T00:24:06.309000Z";
  private static final String TASK3_UPDATED_AT = "2024-01-12T00:24:06.309765Z";
  private static final String TASK3_PUBLISHED_AT = "2024-01-13T00:24:06.309765Z";

  private static final String TASK4_CREATED_AT = "2024-01-12T00:24:06.309000Z";
  private static final String TASK4_UPDATED_AT = "2024-01-12T00:24:06.309765Z";
  private static final int TASK1_TOTAL_QUESTION = 3;
  private static final long TASK1_TOTAL_DURATION = 150;
  private static final int TASK2_TOTAL_QUESTION = 3;
  private static final long TASK2_TOTAL_DURATION = 130;
  private static final int TASK3_TOTAL_QUESTION = 3;
  private static final long TASK3_TOTAL_DURATION = 130;

  private static final int TASK4_TOTAL_QUESTION = 0;
  private static final long TASK4_TOTAL_DURATION = 0;

  private static final int TASK1_TOTAL_WEIGHT = 120;
  private static final int TASK2_TOTAL_WEIGHT = 120;
  private static final int TASK3_TOTAL_WEIGHT = 120;
  private static final int TASK4_TOTAL_WEIGHT = 0;
  private static final UUID DEFAULT_ID = UUID.randomUUID();
  private static final List<TaskQuestion> DEFAULT_TASK_QUESTIONS =
      TaskQuestionDataGenerator.generateTask2QuestionList();
  private static final List<TaskQuestion> TASK1_QUESTIONS =
      TaskQuestionDataGenerator.generateTask1QuestionList();
  private static final List<TaskQuestion> INVALID_INDEXES_TASK_QUESTIONS =
      TaskQuestionDataGenerator.generateInvalidIndexesTaskQuestionList();

  private TaskDataGenerator() {
    throw new UnsupportedOperationException(
        "Utility TaskDataGenerator class cannot be instantiated");
  }

  public static TaskDto generateTask1Dto(UUID id) {
    final TaskDto taskDto = generateTask1Dto();
    taskDto.setId(id);
    return taskDto;
  }

  public static TaskDto generateTaskDtoWithoutIdAndNotExistingQuestionId() {
    return TaskDto.builder()
        .id(null)
        .title(TASK1_TITLE)
        .type(TYPE_QUIZ)
        .descriptionHtml(TASK1_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .taskQuestionDtos(
            TaskQuestionDataGenerator
                .generateTaskQuestionDtoListWithoutIdAndNotExistingQuestionId())
        .published(false)
        .build();
  }

  public static TaskDto generateTaskDtoWithoutQuestionId() {
    return TaskDto.builder()
        .id(null)
        .type(TYPE_QUIZ)
        .title(TASK1_TITLE)
        .descriptionHtml(TASK1_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .taskQuestionDtos(
            TaskQuestionDataGenerator.generateTaskQuestionDtoListWithoutIdAndQuestionId())
        .published(false)
        .build();
  }

  public static TaskDto generateTaskDtoWithoutTaskQuestionId() {
    final TaskDto taskDto = generateTaskDtoWithoutQuestionId();
    taskDto.getTaskQuestionDtos().get(0).setId(null);
    return taskDto;
  }

  public static TaskDto generateTaskDtoWithNotExistingTaskQuestionId() {
    final TaskDto taskDto = generateTask1Dto();
    taskDto.getTaskQuestionDtos().get(0).setId(UUID.randomUUID());
    return taskDto;
  }

  public static TaskDto generateTaskDtoWithoutId() {
    return TaskDto.builder()
        .id(null)
        .type(TYPE_QUIZ)
        .title(TASK1_TITLE)
        .descriptionHtml(TASK1_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .taskQuestionDtos(TaskQuestionDataGenerator.generateTaskQuestionDtoListWithoutId())
        .published(false)
        .build();
  }

  public static TaskDto generateTask1Dto() {
    return TaskDto.builder()
        .id(TASK1_ID)
        .type(TYPE_QUIZ)
        .title(TASK1_TITLE)
        .descriptionHtml(TASK1_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK1_TOTAL_WEIGHT)
        .taskQuestionDtos(TaskQuestionDataGenerator.generateTask1QuestionDtoList())
        .createdAt(ZonedDateTime.parse(TASK1_CREATED_AT))
        .updatedAt(ZonedDateTime.parse(TASK1_UPDATED_AT))
        .totalQuestion(TASK1_TOTAL_QUESTION)
        .totalDuration(TASK1_TOTAL_DURATION)
        .published(false)
        .build();
  }

  public static TaskDto generateTask1DtoAfterDelete1Qq() {
    return TaskDto.builder()
        .id(TASK1_ID)
        .type(TYPE_QUIZ)
        .title(TASK1_TITLE)
        .descriptionHtml(TASK1_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK1_TOTAL_WEIGHT - DEFAULT_WEIGHT)
        .taskQuestionDtos(TaskQuestionDataGenerator.generateTask1QuestionDtoListUpdated())
        .createdAt(ZonedDateTime.parse(TASK1_CREATED_AT))
        .updatedAt(ZonedDateTime.parse(TASK1_UPDATED_AT))
        .totalQuestion(TASK1_TOTAL_QUESTION - 1)
        .totalDuration(TASK1_TOTAL_DURATION - DEFAULT_DURATION)
        .published(false)
        .build();
  }

  public static Task generatePublishedTask2() {
    final AuditEntity auditEntity =
        new AuditEntity(
            ZonedDateTime.parse(TASK2_CREATED_AT), ZonedDateTime.parse(TASK2_UPDATED_AT));
    return Task.builder()
        .id(TASK2_ID)
        .type(TYPE_QUIZ)
        .title(TASK2_TITLE)
        .descriptionHtml(TASK2_DESCRIPTION)
        .passingScore(TASK2_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK2_TOTAL_WEIGHT)
        .taskQuestions(TaskQuestionDataGenerator.generateTask2QuestionList())
        .auditEntity(auditEntity)
        .totalQuestion(TASK2_TOTAL_QUESTION)
        .totalDuration(TASK2_TOTAL_DURATION)
        .published(true)
        .build();
  }

  public static TaskDto generateTask2Dto() {
    return TaskDto.builder()
        .id(TASK2_ID)
        .type(TYPE_QUIZ)
        .title(TASK2_TITLE)
        .descriptionHtml(TASK2_DESCRIPTION)
        .passingScore(TASK2_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK2_TOTAL_WEIGHT)
        .taskQuestionDtos(TaskQuestionDataGenerator.generateTask2QuestionDtoList())
        .createdAt(ZonedDateTime.parse(TASK2_CREATED_AT))
        .updatedAt(ZonedDateTime.parse(TASK2_UPDATED_AT))
        .totalQuestion(TASK2_TOTAL_QUESTION)
        .totalDuration(TASK2_TOTAL_DURATION)
        .published(false)
        .build();
  }

  public static TaskDto generateTask3Dto() {
    return TaskDto.builder()
        .id(TASK3_ID)
        .type(TYPE_QUIZ)
        .title(TASK3_TITLE)
        .descriptionHtml(TASK3_DESCRIPTION)
        .passingScore(TASK3_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK3_TOTAL_WEIGHT)
        .taskQuestionDtos(TaskQuestionDataGenerator.generateTask3QuestionDtoList())
        .createdAt(ZonedDateTime.parse(TASK3_CREATED_AT))
        .updatedAt(ZonedDateTime.parse(TASK3_UPDATED_AT))
        .publishedAt(ZonedDateTime.parse(TASK3_PUBLISHED_AT))
        .totalQuestion(TASK3_TOTAL_QUESTION)
        .totalDuration(TASK3_TOTAL_DURATION)
        .published(true)
        .build();
  }

  public static List<TaskDto> generateTaskDtoList() {
    return List.of(generateTask1Dto(), generateTask2Dto(), generateTask3Dto());
  }

  public static UUID generateExistingTask1Id() {
    return TASK1_ID;
  }

  public static UUID generateExistingTask3Id() {
    return TASK3_ID;
  }

  public static UUID generateNotExistingTaskId() {
    return UUID.randomUUID();
  }

  public static Pageable generateDefaultPageable() {
    return Pageable.ofSize(DEFAULT_PAGE_SIZE);
  }

  public static Task getEntity() {
    return Task.builder()
        .id(DEFAULT_ID)
        .type(TYPE_QUIZ)
        .taskQuestions(DEFAULT_TASK_QUESTIONS)
        .title(TASK1_TITLE)
        .descriptionHtml(DEFAULT_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK1_TOTAL_WEIGHT)
        .published(true)
        .totalDuration(TASK1_TOTAL_DURATION)
        .totalQuestion(TASK1_TOTAL_QUESTION)
        .build();
  }

  public static UUID generateTask4Id() {
    return TASK4_ID;
  }

  public static Task generateTask4WithPracticalTask() {
    return Task.builder()
        .id(TASK4_ID)
        .type(TYPE_PRACTICAL_TASK)
        .taskQuestions(null)
        .title(TASK4_TITLE)
        .descriptionHtml(TASK4_DESCRIPTION)
        .passingScore(TASK4_PASSING_SCORE)
        .maxScore(MAX_GRADE_12)
        .totalWeight(TASK4_TOTAL_WEIGHT)
        .auditEntity(
            new AuditEntity(
                ZonedDateTime.parse(TASK4_CREATED_AT), ZonedDateTime.parse(TASK4_UPDATED_AT)))
        .published(true)
        .totalDuration(TASK4_TOTAL_DURATION)
        .totalQuestion(TASK4_TOTAL_QUESTION)
        .practicalTask(generatePracticalTask())
        .build();
  }

  public static TaskDto generateTask4DtoWithPracticalTask() {
    return TaskDto.builder()
        .id(TASK4_ID)
        .type(TYPE_PRACTICAL_TASK)
        .title(TASK4_TITLE)
        .descriptionHtml(TASK4_DESCRIPTION)
        .passingScore(TASK4_PASSING_SCORE)
        .maxScore(MAX_GRADE_12)
        .totalWeight(TASK4_TOTAL_WEIGHT)
        .createdAt(ZonedDateTime.parse(TASK4_CREATED_AT))
        .updatedAt(ZonedDateTime.parse(TASK4_UPDATED_AT))
        .published(true)
        .totalDuration(TASK4_TOTAL_DURATION)
        .totalQuestion(TASK4_TOTAL_QUESTION)
        .practicalTaskDto(generatePracticalTaskDto())
        .build();
  }

  public static Task generateTaskWithInvalidIndexes() {
    return Task.builder()
        .id(DEFAULT_ID)
        .type(TYPE_QUIZ)
        .taskQuestions(INVALID_INDEXES_TASK_QUESTIONS)
        .title(TASK1_TITLE)
        .descriptionHtml(DEFAULT_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK1_TOTAL_WEIGHT)
        .published(true)
        .totalDuration(TASK1_TOTAL_DURATION)
        .totalQuestion(TASK1_TOTAL_QUESTION)
        .build();
  }

  public static Task generateTaskWithValidIndexes() {
    return Task.builder()
        .id(DEFAULT_ID)
        .type(TYPE_QUIZ)
        .taskQuestions(TASK1_QUESTIONS)
        .title(TASK1_TITLE)
        .descriptionHtml(DEFAULT_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK1_TOTAL_WEIGHT)
        .published(true)
        .totalDuration(TASK1_TOTAL_DURATION)
        .totalQuestion(TASK1_TOTAL_QUESTION)
        .build();
  }

  public static Task generateTaskWithoutTaskQuestions() {
    return Task.builder()
        .id(DEFAULT_ID)
        .type(TYPE_QUIZ)
        .taskQuestions(new ArrayList<>())
        .title(TASK1_TITLE)
        .descriptionHtml(DEFAULT_DESCRIPTION)
        .passingScore(TASK1_PASSING_SCORE)
        .maxScore(MAX_GRADE)
        .totalWeight(TASK1_TOTAL_WEIGHT)
        .published(true)
        .totalDuration(TASK1_TOTAL_DURATION)
        .totalQuestion(TASK1_TOTAL_QUESTION)
        .build();
  }
}
