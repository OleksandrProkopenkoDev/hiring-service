package ua.com.hiringservice.content;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.com.hiringservice.model.content.questioncontent.MatchingTestQuestionContent;

/**
 * Test class which tests Matching Content methods
 *
 * @author Vladislav Sauliak
 */
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.CommentDefaultAccessModifier"})
class MatchingContentTest {

  @ParameterizedTest
  @MethodSource("generateMatchingContent")
  void getGrade(MatchingTestQuestionContent content, Integer expectedGrade) {
    final Integer actualGrade = content.calculateGrade();

    Assertions.assertEquals(expectedGrade, actualGrade, "Should return correct grade");
  }

  private static Stream<Arguments> generateMatchingContent() {
    final String column11 = "column11";
    final String column21 = "column21";
    final String column12 = "column12";
    final String column22 = "column22";
    final String column13 = "column13";
    final String column23 = "column23";
    final String column14 = "column14";
    final String column24 = "column24";
    return Stream.of(
        Arguments.of(
            MatchingTestQuestionContent.builder()
                .correctAnswer(Map.of(column11, column21))
                .providedAnswer(Map.of(column11, column21))
                .maxGrade(MAX_GRADE)
                .build(),
            100),
        Arguments.of(
            MatchingTestQuestionContent.builder()
                .correctAnswer(
                    Map.of(
                        column11, column21, column12, column22, column13, column23, column14,
                        column24))
                .providedAnswer(
                    Map.of(
                        column11, column22, column12, column21, column13, column23, column14,
                        column24))
                .maxGrade(MAX_GRADE)
                .build(),
            50),
        Arguments.of(
            MatchingTestQuestionContent.builder()
                .correctAnswer(Map.of(column11, column21, column12, column22, column13, column23))
                .providedAnswer(Map.of(column11, column22, column12, column21, column13, column23))
                .maxGrade(MAX_GRADE)
                .build(),
            33),
        Arguments.of(
            MatchingTestQuestionContent.builder()
                .correctAnswer(Map.of(column11, column21, column12, column22))
                .providedAnswer(Map.of(column11, column22, column12, column21))
                .maxGrade(MAX_GRADE)
                .build(),
            0),
        Arguments.of(
            MatchingTestQuestionContent.builder()
                .correctAnswer(Map.of())
                .providedAnswer(Map.of())
                .maxGrade(MAX_GRADE)
                .build(),
            0),
        Arguments.of(
            MatchingTestQuestionContent.builder()
                .correctAnswer(Map.of())
                .providedAnswer(Map.of("", ""))
                .maxGrade(MAX_GRADE)
                .build(),
            0));
  }
}
