package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static ua.com.testsupport.constant.TestDataConstant.QUESTIONS_DATA_SQL;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateExistsQuestionId;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateExpectedSizeForFindAllWithAllFiltrationCriteria;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateExpectedSizeForFindAllWithSelectedMaxDuration;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateExpectedSizeForFindAllWithSelectedMinDuration;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateExpectedSizeForFindAllWithSelectedType;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateExpectedSizeForFindAllWithSeveralSelectedType;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateExpectedSizeForFindAllWithoutFiltrationCriteria;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateNotExistsQuestionId;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionDto;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionDtoWithType;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionDtoWithoutId;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionFilterCriteriaWithAllCriterias;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionFilterCriteriaWithMaxDuration;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionFilterCriteriaWithMinDuration;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionFilterCriteriaWithSeveralType;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateQuestionFilterCriteriaWithType;
import static ua.com.testsupport.datagenerator.QuestionDataGenerator.generateUnpagedPageable;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ua.com.hiringservice.IntegrationTestBase;
import ua.com.hiringservice.exception.QuestionNotFoundException;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.questioncontent.MatchingTestQuestionContent;
import ua.com.hiringservice.model.dto.filter.QuestionFilterCriteria;
import ua.com.hiringservice.model.dto.task.QuestionDto;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.service.task.QuestionService;
import ua.com.hiringservice.service.task.impl.QuestionServiceImpl;
import ua.com.testsupport.datagenerator.contentdata.MatchingTestQuestionContentData;

/**
 * JUnit test class for {@link QuestionServiceImpl}.
 *
 * <p>This class contains test cases for various methods in the {@link QuestionServiceImpl}
 * implementation of the {@link QuestionService} interface. It covers scenarios such as finding
 * questions by ID, filtering questions based on criteria, saving new questions, updating existing
 * questions, and deleting questions by ID.
 *
 * <p>Integration tests are performed, and the test data is initialized using SQL scripts. Mocked
 * data generators and predefined constants are used for consistent and repeatable test scenarios.
 *
 * <p>Note: The actual implementation of the QuestionServiceImpl and its dependencies is expected to
 * be properly configured in the Spring context for successful execution of these tests.
 *
 * @author Vladislav Sauliak
 * @version 1.0
 * @since 2024-01-12
 */
@Sql(scripts = QUESTIONS_DATA_SQL)
class QuestionServiceImplIntegrationTest extends IntegrationTestBase {

  @Autowired private QuestionService questionService;

  @Test
  void findById_shouldReturnQuestion_whenItExists() {
    final UUID questionId = generateExistsQuestionId();
    final QuestionDto expected = generateQuestionDto();

    final QuestionDto actual = questionService.findById(questionId);

    assertEquals(expected, actual, "Find by id should return Question when it exists in database");
  }

  @Test
  void findById_shouldReturnException_whenItNotExists() {
    final UUID questionId = generateNotExistsQuestionId();

    assertThrows(
        QuestionNotFoundException.class,
        () -> questionService.findById(questionId),
        "Find by id should throw exception when Question not exists in database");
  }

  @Test
  void findAllByFilterCriteria_shouldReturnQuestions_whenSelectedType() {
    final QuestionFilterCriteria questionFilterCriteria = generateQuestionFilterCriteriaWithType();
    final Pageable pageable = generateUnpagedPageable();
    final Integer expectedSize = generateExpectedSizeForFindAllWithSelectedType();

    final Page<QuestionDto> questions =
        questionService.findAllByFilterCriteria(pageable, questionFilterCriteria);

    assertEquals(
        expectedSize,
        questions.getContent().size(),
        "Find all by criteria should return list of Questions when only one type was passed");
  }

  @Test
  void findAllByFilterCriteria_shouldReturnQuestions_whenSeveralSelectedType() {
    final QuestionFilterCriteria questionFilterCriteria =
        generateQuestionFilterCriteriaWithSeveralType();
    final Pageable pageable = generateUnpagedPageable();
    final Integer expectedSize = generateExpectedSizeForFindAllWithSeveralSelectedType();

    final Page<QuestionDto> questions =
        questionService.findAllByFilterCriteria(pageable, questionFilterCriteria);

    assertEquals(
        expectedSize,
        questions.getContent().size(),
        "Find all by criteria should return list of Questions when several types was passed");
  }

  @Test
  void findAllByFilterCriteria_shouldReturnQuestions_whenSelectedMaxDuration() {
    final QuestionFilterCriteria questionFilterCriteria =
        generateQuestionFilterCriteriaWithMaxDuration();
    final Pageable pageable = generateUnpagedPageable();
    final Integer expectedSize = generateExpectedSizeForFindAllWithSelectedMaxDuration();

    final Page<QuestionDto> questions =
        questionService.findAllByFilterCriteria(pageable, questionFilterCriteria);

    assertEquals(
        expectedSize,
        questions.getContent().size(),
        "Find all by criteria should return list of Questions when max duration was passed");
  }

  @Test
  void findAllByFilterCriteria_shouldReturnQuestions_whenSelectedMinDuration() {
    final QuestionFilterCriteria questionFilterCriteria =
        generateQuestionFilterCriteriaWithMinDuration();
    final Pageable pageable = generateUnpagedPageable();
    final Integer expectedSize = generateExpectedSizeForFindAllWithSelectedMinDuration();

    final Page<QuestionDto> questions =
        questionService.findAllByFilterCriteria(pageable, questionFilterCriteria);

    assertEquals(
        expectedSize,
        questions.getContent().size(),
        "Find all by criteria should return list of Questions when min duration was passed");
  }

  @Test
  void findAllByFilterCriteria_shouldReturnQuestions_whenSelectedAllCriteria() {
    final QuestionFilterCriteria questionFilterCriteria =
        generateQuestionFilterCriteriaWithAllCriterias();
    final Pageable pageable = generateUnpagedPageable();
    final Integer expectedSize = generateExpectedSizeForFindAllWithAllFiltrationCriteria();

    final Page<QuestionDto> questions =
        questionService.findAllByFilterCriteria(pageable, questionFilterCriteria);

    assertEquals(
        expectedSize,
        questions.getContent().size(),
        "Find all by criteria should return list of Questions when all of criterias was passed");
  }

  @Test
  void findAllByFilterCriteria_shouldReturnQuestions_whenQuestionFilterCriteriaEmpty() {
    final QuestionFilterCriteria questionFilterCriteria = new QuestionFilterCriteria();
    final Pageable pageable = generateUnpagedPageable();
    final Integer expectedSize = generateExpectedSizeForFindAllWithoutFiltrationCriteria();

    final Page<QuestionDto> questions =
        questionService.findAllByFilterCriteria(pageable, questionFilterCriteria);

    assertEquals(
        expectedSize,
        questions.getContent().size(),
        "Find all by criteria should return list of Questions when none of criterias was passed");
  }

  @Test
  void save() {
    final QuestionDto questionDto = generateQuestionDtoWithoutId();

    final QuestionDto actual = questionService.save(questionDto);

    assertNotNull(actual.getId(), "Save should persist entity in db and return dto");
  }

  @Test
  void update_shouldReturnUpdatedDto_whenQuestionIsFound() {
    final UUID questionId = generateExistsQuestionId();
    final QuestionDto questionDto = generateQuestionDtoWithType(QuestionType.SINGLE_CHOICE_TO_APP);

    final QuestionDto actual = questionService.update(questionId, questionDto);

    assertEquals(
        questionDto.getContent().getQuestionType(),
        actual.getContent().getQuestionType(),
        "Update by id should update entity by id according to new questionDto");
  }

  @Test
  void deleteById() {
    final UUID questionId = generateExistsQuestionId();

    questionService.deleteById(questionId);

    assertThrows(
        QuestionNotFoundException.class,
        () -> questionService.findById(questionId),
        "Delete by id. Test assertion should retrieve exception if question not exists(which means"
            + " it's been deleted.)");
  }

  /**
   * The test checks whether the content of the saved question is an instance of {@link
   * MatchingTestQuestionContent}, asserting that the content serialization and deserialization
   * process preserves the specific content type.
   *
   * @see QuestionService
   * @see QuestionDto
   * @see MatchingTestQuestionContentData
   * @see QuestionType
   * @see MatchingTestQuestionContent
   */
  @Test
  void findById_shouldReturnQuestionWithContentInstanceSameLikeSaved_providedCorrectQuestion() {
    QuestionDto questionDto = generateQuestionDtoWithoutId();
    final Content expected = MatchingTestQuestionContentData.getJsonMatchInstance();
    questionDto.setContent(expected);

    questionDto = questionService.save(questionDto);
    final QuestionDto savedQuestionDto = questionService.findById(questionDto.getId());

    final Content actual = savedQuestionDto.getContent();
    final boolean check = actual instanceof MatchingTestQuestionContent;

    assertTrue(
        check,
        """
                      Content of found by id question have to be instance of
                      MatchingTestQuestionContent""");
    assertEquals(
        expected,
        actual,
        """
                      Content of found by id question have to be equals to content of the same
                      question before saving in to database
                      MatchingTestQuestionContent""");
  }
}
