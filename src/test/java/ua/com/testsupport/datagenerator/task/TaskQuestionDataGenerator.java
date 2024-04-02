package ua.com.testsupport.datagenerator.task;

import java.util.List;
import java.util.UUID;
import ua.com.hiringservice.model.dto.task.QuestionDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.entity.task.TaskQuestion;
import ua.com.testsupport.datagenerator.QuestionDataGenerator;

/**
 * The TaskQuestionDataGenerator class provides utility methods for generating TaskQuestionDto
 * objects and lists for task questions, including different scenarios and sequences.
 *
 * <p>This class includes methods to generate TaskQuestionDto objects with or without IDs,
 * sequences, and associated QuestionDto objects. It is designed as a utility class and cannot be
 * instantiated.
 *
 * @author Oleksandr Prokopenko
 * @version 1.0
 * @since 2024-01-12
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public final class TaskQuestionDataGenerator {

  private static final UUID TASK1_QUESTION1_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
  private static final UUID TASK1_QUESTION2_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
  private static final UUID TASK1_QUESTION3_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440003");
  private static final UUID TASK2_QUESTION1_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440011");
  private static final UUID TASK2_QUESTION2_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440012");
  private static final UUID TASK2_QUESTION3_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440013");

  private static final UUID TASK3_QUESTION1_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440021");
  private static final UUID TASK3_QUESTION2_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440022");
  private static final UUID TASK3_QUESTION3_ID =
      UUID.fromString("550e8400-e29b-41d4-a716-446655440023");

  private static final UUID NOT_EXISTING_TASK_QUESTION_ID =
      UUID.fromString("450e8400-1111-41d4-a716-446655440000");

  public static final Integer DEFAULT_WEIGHT = 40;
  private static final Integer UPDATED_WEIGHT = 120;
  private static final Integer SEQUENCE_FIRST = 1;
  private static final Integer SEQUENCE_SECOND = 2;
  private static final Integer SEQUENCE_THIRD = 3;
  private static final Integer SEQUENCE_FOUR = 4;
  private static final Integer SEQUENCE_FIVE = 5;

  private TaskQuestionDataGenerator() {
    throw new UnsupportedOperationException(
        "Utility TaskQuestionDataGenerator class cannot be instantiated");
  }

  public static List<TaskQuestionDto>
      generateTaskQuestionDtoListWithoutIdAndNotExistingQuestionId() {
    return List.of(
        generateTaskQuestionDto(
            null, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestionDtoWithNotExistingId()),
        generateTaskQuestionDto(
            null, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestionDtoWithNotExistingId()),
        generateTaskQuestionDto(
            null, SEQUENCE_THIRD, QuestionDataGenerator.generateQuestionDtoWithNotExistingId()));
  }

  public static List<TaskQuestionDto> generateTaskQuestionDtoListWithoutIdAndQuestionId() {
    return List.of(
        generateTaskQuestionDto(
            null, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestionDtoWithoutId()),
        generateTaskQuestionDto(
            null, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestionDtoWithoutId()),
        generateTaskQuestionDto(
            null, SEQUENCE_THIRD, QuestionDataGenerator.generateQuestionDtoWithoutId()));
  }

  public static List<TaskQuestionDto> generateTaskQuestionDtoListWithoutId() {
    return List.of(
        generateTaskQuestionDto(null, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestion1Dto()),
        generateTaskQuestionDto(
            null, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestion2Dto()),
        generateTaskQuestionDto(
            null, SEQUENCE_THIRD, QuestionDataGenerator.generateQuestion3Dto()));
  }

  public static List<TaskQuestionDto> generateTask1QuestionDtoList() {
    return List.of(
        generateTaskQuestionDto(
            TASK1_QUESTION1_ID, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestion1Dto()),
        generateTaskQuestionDto(
            TASK1_QUESTION2_ID, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestion2Dto()),
        generateTaskQuestionDto(
            TASK1_QUESTION3_ID, SEQUENCE_THIRD, QuestionDataGenerator.generateQuestion3Dto()));
  }

  public static List<TaskQuestionDto> generateTask1QuestionDtoListUpdated() {
    return List.of(
        generateTaskQuestionDto(
            TASK1_QUESTION2_ID, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestion2Dto()),
        generateTaskQuestionDto(
            TASK1_QUESTION3_ID, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestion3Dto()));
  }

  public static List<TaskQuestionDto> generateTask2QuestionDtoList() {
    return List.of(
        generateTaskQuestionDto(
            TASK2_QUESTION1_ID, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestion3Dto()),
        generateTaskQuestionDto(
            TASK2_QUESTION2_ID, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestion4Dto()),
        generateTaskQuestionDto(
            TASK2_QUESTION3_ID, SEQUENCE_THIRD, QuestionDataGenerator.generateQuestion5Dto()));
  }

  public static List<TaskQuestion> generateTask2QuestionList() {
    return List.of(
        generateTaskQuestion(
            TASK2_QUESTION1_ID, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestion3()),
        generateTaskQuestion(
            TASK2_QUESTION2_ID, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestion4()),
        generateTaskQuestion(
            TASK2_QUESTION3_ID, SEQUENCE_THIRD, QuestionDataGenerator.generateQuestion5()));
  }

  public static List<TaskQuestionDto> generateTask3QuestionDtoList() {
    return List.of(
        generateTaskQuestionDto(
            TASK3_QUESTION1_ID, SEQUENCE_FIRST, QuestionDataGenerator.generateQuestion3Dto()),
        generateTaskQuestionDto(
            TASK3_QUESTION2_ID, SEQUENCE_SECOND, QuestionDataGenerator.generateQuestion4Dto()),
        generateTaskQuestionDto(
            TASK3_QUESTION3_ID, SEQUENCE_THIRD, QuestionDataGenerator.generateQuestion5Dto()));
  }

  private static TaskQuestionDto generateTaskQuestionDto(
      final UUID id, final Integer sequence, final QuestionDto questionDto) {
    return TaskQuestionDto.builder()
        .id(id)
        .weight(DEFAULT_WEIGHT)
        .indexInTask(sequence)
        .questionDto(questionDto)
        .build();
  }

  private static TaskQuestion generateTaskQuestion(
      final UUID id, final Integer sequence, final Question question) {
    return TaskQuestion.builder()
        .id(id)
        .weight(DEFAULT_WEIGHT)
        .indexInTask(sequence)
        .question(question)
        .build();
  }

  public static TaskQuestionDto generateTaskQuestion1Dto() {
    return TaskQuestionDto.builder()
        .id(null)
        .weight(DEFAULT_WEIGHT)
        .indexInTask(SEQUENCE_FOUR)
        .questionDto(QuestionDataGenerator.generateQuestion1Dto())
        .build();
  }

  public static TaskQuestion generateTaskQuestion1() {
    return TaskQuestion.builder()
        .id(TASK1_QUESTION1_ID)
        .weight(DEFAULT_WEIGHT)
        .indexInTask(SEQUENCE_FOUR)
        .question(QuestionDataGenerator.generateQuestion1())
        .build();
  }

  public static TaskQuestionDto generateTaskQuestion1DtoUpdated() {
    return TaskQuestionDto.builder()
        .id(null)
        .weight(UPDATED_WEIGHT)
        .indexInTask(SEQUENCE_FIVE)
        .questionDto(QuestionDataGenerator.generateQuestion1DtoUpdated())
        .build();
  }

  public static TaskQuestionDto generateTaskQuestion1DtoWithSequence1() {
    return TaskQuestionDto.builder()
        .id(null)
        .weight(DEFAULT_WEIGHT)
        .indexInTask(SEQUENCE_FIRST)
        .questionDto(QuestionDataGenerator.generateQuestion1Dto())
        .build();
  }

  public static TaskQuestionDto generateTaskQuestion4Dto() {
    return TaskQuestionDto.builder()
        .id(null)
        .weight(DEFAULT_WEIGHT)
        .indexInTask(SEQUENCE_FOUR)
        .questionDto(QuestionDataGenerator.generateQuestion4Dto())
        .build();
  }

  public static UUID generateNotExistingTaskQuestionId() {
    return NOT_EXISTING_TASK_QUESTION_ID;
  }

  public static UUID generateExistingTask1Question1Id() {
    return TASK1_QUESTION1_ID;
  }

  public static UUID generateExistingTask3Question1Id() {
    return TASK3_QUESTION1_ID;
  }

  public static UUID generateExistingTask2Question1Id() {
    return TASK2_QUESTION1_ID;
  }

  public static TaskQuestion getEntity() {
    return TaskQuestion.builder()
        .id(TASK1_QUESTION1_ID)
        .question(QuestionDataGenerator.getEntity())
        .weight(DEFAULT_WEIGHT)
        .indexInTask(SEQUENCE_FIRST)
        .build();
  }

  public static TaskQuestion getEntity(
      final UUID taskQuestionId, final int sequence, final Question question) {
    final TaskQuestion taskQuestion = getEntity();
    taskQuestion.setId(taskQuestionId);
    taskQuestion.setIndexInTask(sequence);
    taskQuestion.setQuestion(question);
    return taskQuestion;
  }

  /*  public static List<TaskQuestion> generateTask2QuestionList() {
    return List.of(
        getEntity(
            TASK2_QUESTION1_ID, SEQUENCE_FIRST, QuestionDataGenerator.getEntity(UUID.randomUUID())),
        getEntity(
            TASK2_QUESTION2_ID,
            SEQUENCE_SECOND,
            QuestionDataGenerator.getEntity(UUID.randomUUID())),
        getEntity(
            TASK2_QUESTION3_ID,
            SEQUENCE_THIRD,
            QuestionDataGenerator.getEntity(UUID.randomUUID())));
  }*/

  public static List<TaskQuestion> generateTask1QuestionList() {
    return List.of(
        getEntity(TASK1_QUESTION1_ID, SEQUENCE_SECOND, QuestionDataGenerator.getQuestion1()),
        getEntity(TASK1_QUESTION2_ID, SEQUENCE_FIRST, QuestionDataGenerator.getQuestion2()),
        getEntity(TASK1_QUESTION3_ID, SEQUENCE_THIRD, QuestionDataGenerator.getQuestion3()));
  }

  public static List<TaskQuestion> generateInvalidIndexesTaskQuestionList() {
    return List.of(
        getEntity(TASK1_QUESTION1_ID, SEQUENCE_THIRD, QuestionDataGenerator.getQuestion1()),
        getEntity(TASK1_QUESTION2_ID, SEQUENCE_FIRST, QuestionDataGenerator.getQuestion2()),
        getEntity(TASK1_QUESTION3_ID, SEQUENCE_FIVE, QuestionDataGenerator.getQuestion3()));
  }
}
