package ua.com.testsupport.datagenerator;

import static ua.com.hiringservice.model.enums.exam.QuestionType.MULTIPLE_CHOICE_TO_APP;
import static ua.com.hiringservice.model.enums.exam.QuestionType.SINGLE_CHOICE_TO_APP;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.dto.filter.QuestionFilterCriteria;
import ua.com.hiringservice.model.dto.task.QuestionDto;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.testsupport.datagenerator.contentdata.MatchingTestQuestionContentData;
import ua.com.testsupport.datagenerator.contentdata.MultipleChoiceTestQuestionContentData;
import ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData;

/** Please, replace this stub with valid comment. */
public class QuestionDataGenerator {
  private static final UUID EXISTING_QUESTION_ID =
      UUID.fromString("baa8bb7d-9533-4483-bdb4-204efba55e7a");
  private static final UUID QUESTION2_ID = UUID.fromString("2bc5530e-0eb3-4ed7-8f74-cbc4cbf7b5bb");
  private static final UUID QUESTION3_ID = UUID.fromString("570f93b5-485c-42ea-82f9-8c866ac0e4c4");
  private static final UUID QUESTION4_ID = UUID.fromString("024890e3-c883-4049-947b-8af2f9a6bfdf");
  private static final UUID QUESTION5_ID = UUID.fromString("e4f282d8-4308-44e8-a47c-687e556ead24");
  private static final UUID NOT_EXISTING_QUESTION_ID = UUID.randomUUID();
  private static final QuestionType DEFAULT_QUESTION_TYPE = SINGLE_CHOICE_TO_APP;
  public static final Long DEFAULT_DURATION = 50L;
  private static final String DEFAULT_DESCRIPTION = "comment";

  private static final Content SINGLE_CHOICE_TEST_QUESTION =
      SingleChoiceTestQuestionContentData.getJsonMatchInstance();
  private static final Content MULTIPLE_CHOICE_TEST_QUESTION =
      MultipleChoiceTestQuestionContentData.getJsonMatchInstance();
  private static final Content MATCHING_TO_APP_QUESTION =
      MatchingTestQuestionContentData.getJsonMatchInstance();
  private static final List<QuestionType> DEFAULT_LIST_WITH_SEVERAL_QUESTION_TYPES =
      List.of(SINGLE_CHOICE_TO_APP, MULTIPLE_CHOICE_TO_APP);
  private static final Long DEFAULT_MAX_DURATION = 60L;
  private static final Long DEFAULT_MIN_DURATION = 40L;
  private static final Pageable DEFAULT_UNPAGED_PAGEABLE = Pageable.unpaged();
  private static final Integer EXPECTED_SIZE_SELECTED_TYPE = 2;
  private static final Integer EXPECTED_SIZE_SEVERAL_SELECTED_TYPE = 3;
  private static final Integer EXPECTED_SIZE_MAX_DURATION = 4;
  private static final Integer EXPECTED_SIZE_MIN_DURATION = 4;
  private static final Integer EXPECTED_SIZE_WITH_CRITERIA = 3;
  private static final Integer EXPECTED_SIZE_WITHOUT_CRITERIA = 5;

  public static UUID generateExistsQuestionId() {
    return EXISTING_QUESTION_ID;
  }

  public static UUID generateNotExistsQuestionId() {
    return NOT_EXISTING_QUESTION_ID;
  }

  public static QuestionDto generateQuestionDto() {
    return QuestionDto.builder()
        .id(EXISTING_QUESTION_ID)
        .duration(DEFAULT_DURATION)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestionDtoWithType(final QuestionType type) {
    return QuestionDto.builder()
        .id(EXISTING_QUESTION_ID)
        .duration(DEFAULT_DURATION)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestionDtoWithoutId() {
    return QuestionDto.builder()
        .duration(DEFAULT_DURATION)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestion1Dto() {
    return generateQuestionDto();
  }

  public static Question generateQuestion1() {
    return Question.builder()
        .id(EXISTING_QUESTION_ID)
        .duration(DEFAULT_DURATION)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestion1DtoUpdated() {
    return QuestionDto.builder()
        .id(EXISTING_QUESTION_ID)
        .duration(DEFAULT_DURATION + DEFAULT_DURATION)
        .comment(DEFAULT_DESCRIPTION + DEFAULT_DESCRIPTION)
        .content(MULTIPLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestion2Dto() {
    return QuestionDto.builder()
        .id(QUESTION2_ID)
        .duration(60L)
        .comment(DEFAULT_DESCRIPTION)
        .content(MULTIPLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestion3Dto() {
    return QuestionDto.builder()
        .id(QUESTION3_ID)
        .duration(40L)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static Question generateQuestion3() {
    return Question.builder()
        .id(QUESTION3_ID)
        .duration(40L)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestion4Dto() {
    return QuestionDto.builder()
        .id(QUESTION4_ID)
        .duration(70L)
        .comment(DEFAULT_DESCRIPTION)
        .content(MATCHING_TO_APP_QUESTION)
        .build();
  }

  public static Question generateQuestion4() {
    return Question.builder()
        .id(QUESTION4_ID)
        .duration(70L)
        .comment(DEFAULT_DESCRIPTION)
        .content(MATCHING_TO_APP_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestion5Dto() {
    return QuestionDto.builder()
        .id(QUESTION5_ID)
        .duration(20L)
        .comment(DEFAULT_DESCRIPTION)
        .content(MATCHING_TO_APP_QUESTION)
        .build();
  }

  public static Question generateQuestion5() {
    return Question.builder()
        .id(QUESTION5_ID)
        .duration(20L)
        .comment(DEFAULT_DESCRIPTION)
        .content(MATCHING_TO_APP_QUESTION)
        .build();
  }

  public static QuestionDto generateQuestionDtoWithNotExistingId() {
    return QuestionDto.builder()
        .id(NOT_EXISTING_QUESTION_ID)
        .duration(DEFAULT_DURATION)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static QuestionFilterCriteria generateQuestionFilterCriteriaWithType() {
    return QuestionFilterCriteria.builder().questionType(List.of(DEFAULT_QUESTION_TYPE)).build();
  }

  public static QuestionFilterCriteria generateQuestionFilterCriteriaWithSeveralType() {
    return QuestionFilterCriteria.builder()
        .questionType(DEFAULT_LIST_WITH_SEVERAL_QUESTION_TYPES)
        .build();
  }

  public static QuestionFilterCriteria generateQuestionFilterCriteriaWithMaxDuration() {
    return QuestionFilterCriteria.builder().maxDuration(DEFAULT_MAX_DURATION).build();
  }

  public static QuestionFilterCriteria generateQuestionFilterCriteriaWithMinDuration() {
    return QuestionFilterCriteria.builder().minDuration(DEFAULT_MIN_DURATION).build();
  }

  public static QuestionFilterCriteria generateQuestionFilterCriteriaWithAllCriterias() {
    return QuestionFilterCriteria.builder()
        .questionType(DEFAULT_LIST_WITH_SEVERAL_QUESTION_TYPES)
        .maxDuration(DEFAULT_MAX_DURATION)
        .minDuration(DEFAULT_MIN_DURATION)
        .build();
  }

  public static Pageable generateUnpagedPageable() {
    return DEFAULT_UNPAGED_PAGEABLE;
  }

  public static Integer generateExpectedSizeForFindAllWithSelectedType() {
    return EXPECTED_SIZE_SELECTED_TYPE;
  }

  public static Integer generateExpectedSizeForFindAllWithSeveralSelectedType() {
    return EXPECTED_SIZE_SEVERAL_SELECTED_TYPE;
  }

  public static Integer generateExpectedSizeForFindAllWithSelectedMaxDuration() {
    return EXPECTED_SIZE_MAX_DURATION;
  }

  public static Integer generateExpectedSizeForFindAllWithSelectedMinDuration() {
    return EXPECTED_SIZE_MIN_DURATION;
  }

  public static Integer generateExpectedSizeForFindAllWithAllFiltrationCriteria() {
    return EXPECTED_SIZE_WITH_CRITERIA;
  }

  public static Integer generateExpectedSizeForFindAllWithoutFiltrationCriteria() {
    return EXPECTED_SIZE_WITHOUT_CRITERIA;
  }

  public static Question getEntity() {
    return Question.builder()
        .duration(DEFAULT_DURATION)
        .comment(DEFAULT_DESCRIPTION)
        .content(SINGLE_CHOICE_TEST_QUESTION)
        .build();
  }

  public static Question getEntity(final UUID id) {
    final Question question = getEntity();
    question.setId(id);
    return question;
  }

  public static Question getQuestion1() {
    final Question question = getEntity();
    question.setId(EXISTING_QUESTION_ID);
    return question;
  }

  public static Question getQuestion2() {
    final Question question = getEntity();
    question.setId(QUESTION2_ID);
    return question;
  }

  public static Question getQuestion3() {
    final Question question = getEntity();
    question.setId(QUESTION3_ID);
    return question;
  }

  public static Question getQuestion4() {
    final Question question = getEntity();
    question.setId(QUESTION4_ID);
    return question;
  }

  public static Question getQuestion5() {
    final Question question = getEntity();
    question.setId(QUESTION5_ID);
    return question;
  }
}
