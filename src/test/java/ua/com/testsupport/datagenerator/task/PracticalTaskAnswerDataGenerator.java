package ua.com.testsupport.datagenerator.task;

import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskPassingForPracticalTask;
import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE_12;

import java.util.UUID;
import ua.com.hiringservice.model.dto.task.AssessPracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskAnswerDto;
import ua.com.hiringservice.model.dto.task.ProvidePracticalTaskAnswerDto;
import ua.com.hiringservice.model.entity.task.PracticalTaskAnswer;

/**
 * This class provides methods to generate test data for practical task answers, including images
 * and DTOs.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public final class PracticalTaskAnswerDataGenerator {

  private static final UUID PRACTICAL_TASK_ANSWER_ID1 =
      UUID.fromString("be45931d-309b-4e27-a95f-1530ac01ab7d");
  private static final String GIT_ANSWER_LINK1 = "https://githab.com/provided-solution";
  private static final String COMMENT_FROM_APPLICANT1 =
      "This is comment from applicant: I provided my best solution.";
  private static final String COMMENT_FROM_REVIEWER1 = "This is comment from reviewer: good job!";
  private static final Integer GRADE_FOR_PRACTICAL_TASK = 7;

  private PracticalTaskAnswerDataGenerator() {
    throw new UnsupportedOperationException(
        "Utility PracticalTaskAnswerDataGenerator class cannot be instantiated");
  }

  public static UUID generatePracticalTaskAnswerId1() {
    return PRACTICAL_TASK_ANSWER_ID1;
  }

  public static ProvidePracticalTaskAnswerDto generateProvidedPracticalTaskAnswerDto() {
    return ProvidePracticalTaskAnswerDto.builder()
        .gitAnswerLink(GIT_ANSWER_LINK1)
        .comment(COMMENT_FROM_APPLICANT1)
        .build();
  }

  public static PracticalTaskAnswer generatePracticalTaskAnswer() {
    return PracticalTaskAnswer.builder()
        .id(PRACTICAL_TASK_ANSWER_ID1)
        .taskPassing(generateTaskPassingForPracticalTask())
        .maxScore(MAX_GRADE_12)
        .gitAnswerLink(GIT_ANSWER_LINK1)
        .comment(COMMENT_FROM_REVIEWER1)
        .build();
  }

  public static PracticalTaskAnswerDto generatePracticalTaskAnswerDto() {
    return PracticalTaskAnswerDto.builder()
        .id(PRACTICAL_TASK_ANSWER_ID1)
        .taskPassing(generateTaskPassingForPracticalTask())
        .maxScore(MAX_GRADE_12)
        .gitAnswerLink(GIT_ANSWER_LINK1)
        .comment(COMMENT_FROM_REVIEWER1)
        .build();
  }

  public static ProvidePracticalTaskAnswerDto generateProvidePracticalTaskAnswerDto() {
    return ProvidePracticalTaskAnswerDto.builder()
        .gitAnswerLink(GIT_ANSWER_LINK1)
        .comment(COMMENT_FROM_APPLICANT1)
        .build();
  }

  public static PracticalTaskAnswer generateEmptyPracticalTaskAnswer() {
    return PracticalTaskAnswer.builder()
        .id(PRACTICAL_TASK_ANSWER_ID1)
        .taskPassing(generateTaskPassingForPracticalTask())
        .maxScore(MAX_GRADE_12)
        .build();
  }

  public static AssessPracticalTaskDto generateAssessPracticalTaskDto() {
    return AssessPracticalTaskDto.builder()
        .score(GRADE_FOR_PRACTICAL_TASK)
        .comment(COMMENT_FROM_REVIEWER1)
        .build();
  }
}
