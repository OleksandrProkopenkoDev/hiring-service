package ua.com.testsupport.datagenerator;

import static ua.com.testsupport.datagenerator.KeycloakDataGenerator.generateUserKeycloakIdData;
import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE_12;
import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.getJsonMatchInstance;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generateProvidePracticalTaskAnswerDto;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4Id;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4WithPracticalTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ua.com.hiringservice.model.dto.task.PracticalTaskPassingResponseDto;
import ua.com.hiringservice.model.dto.task.TaskPassingDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.entity.task.TaskQuestion;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.TaskType;
import ua.com.testsupport.datagenerator.task.TaskDataGenerator;

/** Replace this stub by correct Javadoc. */
public class TaskPassingDataGenerator {

  private static final UUID USER_KEYCLOAK_ID = UUID.randomUUID();
  private static final UUID TASK_ID = UUID.randomUUID();
  private static final Integer SEQUENCE = 1;
  private static final Integer WEIGHT = 1;
  private static final UUID TASK_PASSING_ID1_PRACTICAL =
      UUID.fromString("1800b085-8c10-4d7e-9b9e-81472dcc336f");
  private static final PassingStatus TASK_PASSING_IN_PROGRESS = PassingStatus.IN_PROGRESS;
  private static final PassingStatus TASK_PASSING_FINISHED = PassingStatus.ANSWERED;
  private static final PassingStatus DEFAULT_STATUS = PassingStatus.ANSWERED;

  private static final UUID USER_ID_WITH_FINISHED_TASK_PASSING =
      UUID.fromString("981e1e88-1bd2-47e6-b975-c644f2c33ae1");
  private static final UUID USER_ID_WITH_IN_PROGRESS_TASK_PASSING =
      UUID.fromString("981e1e88-1bd2-47e6-b975-c644f2c33ae2");
  private static final UUID USER_ID_WITH_GRADED_TASK_PASSING =
      UUID.fromString("981e1e88-1bd2-47e6-b975-c644f2c33ae1");
  private static final UUID TASK_ID_WITH_GRADED_TASK_PASSING =
      UUID.fromString("550e8400-1111-41d4-a716-446655440002");

  public static UUID generateUserIdWithFinishedTaskPassing() {
    return USER_ID_WITH_FINISHED_TASK_PASSING;
  }

  public static UUID generateUserIdWithGradedTaskPassing() {
    return USER_ID_WITH_GRADED_TASK_PASSING;
  }

  public static UUID generateTaskIdWithGradedTaskPassing() {
    return TASK_ID_WITH_GRADED_TASK_PASSING;
  }

  public static UUID generateUserIdWithInProgressTaskPassing() {
    return USER_ID_WITH_IN_PROGRESS_TASK_PASSING;
  }

  public static UUID generateUserKeycloakId() {
    return USER_KEYCLOAK_ID;
  }

  public static UUID generateTaskId() {
    return TASK_ID;
  }

  public static Task generatePublishedTask() {
    return Task.builder()
        .id(TASK_ID)
        .taskQuestions(
            List.of(
                TaskQuestion.builder()
                    .indexInTask(SEQUENCE)
                    .weight(WEIGHT)
                    .question(Question.builder().build())
                    .build()))
        .published(true)
        .build();
  }

  public static TaskPassing generateTaskPassingInProgress() {
    return TaskPassing.builder()
        .status(TASK_PASSING_IN_PROGRESS)
        .task(generatePublishedTask())
        .answers(List.of(Answer.builder().content(getJsonMatchInstance()).build()))
        .build();
  }

  public static UUID generateTaskPassingIdPractical() {
    return TASK_PASSING_ID1_PRACTICAL;
  }

  public static TaskPassing generateTaskPassingForPracticalTask() {
    return TaskPassing.builder()
        .id(TASK_PASSING_ID1_PRACTICAL)
        .status(TASK_PASSING_IN_PROGRESS)
        .task(generateTask4WithPracticalTask())
        .answers(null)
        .practicalTaskAnswer(null)
        .score(MAX_GRADE_12)
        .userKeycloakId(generateUserKeycloakIdData())
        .build();
  }

  public static PracticalTaskPassingResponseDto generatePracticalTaskPassingResponseDto() {
    return PracticalTaskPassingResponseDto.builder()
        .id(TASK_PASSING_ID1_PRACTICAL)
        .taskId(generateTask4Id())
        .passingScore(generateTask4WithPracticalTask().getPassingScore())
        .comment(generateTask4WithPracticalTask().getComment())
        .maxScore(generateTask4WithPracticalTask().getMaxScore())
        .score(generateTaskPassingForPracticalTask().getScore())
        .type(TaskType.PRACTICAL_TASK)
        .status(PassingStatus.GRADED)
        .practicalTaskAnswer(generateProvidePracticalTaskAnswerDto())
        .build();
  }

  public static TaskPassing generateTaskPassingFinished() {
    return TaskPassing.builder()
        .status(TASK_PASSING_FINISHED)
        .answers(List.of(Answer.builder().build()))
        .build();
  }

  public static Task generateNotPublishedTask() {
    return Task.builder()
        .id(TASK_ID)
        .type(TaskType.QUIZ)
        .taskQuestions(
            List.of(
                TaskQuestion.builder()
                    .indexInTask(SEQUENCE)
                    .weight(WEIGHT)
                    .question(Question.builder().build())
                    .build()))
        .published(false)
        .build();
  }

  public static Integer generateSequence() {
    return SEQUENCE;
  }

  public static TaskPassing generateTaskPassingWithoutAnswers() {
    return TaskPassing.builder().answers(new ArrayList<>()).build();
  }

  public static TaskPassing generateTaskPassingWithAnswers() {
    return TaskPassing.builder()
        .answers(
            List.of(Answer.builder().indexInTask(SEQUENCE).content(getJsonMatchInstance()).build()))
        .build();
  }

  public static TaskPassingDto toDto(final TaskPassing taskPassing) {
    return TaskPassingDto.builder().status(taskPassing.getStatus()).id(taskPassing.getId()).build();
  }

  public static TaskPassing getEntity() {
    return TaskPassing.builder().task(TaskDataGenerator.getEntity()).status(DEFAULT_STATUS).build();
  }
}
