package ua.com.hiringservice.content;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.com.hiringservice.model.content.questioncontent.SingleChoiceTestQuestionContent;

/**
 * Test class which tests Single Content methods
 *
 * @author Vladislav Sauliak
 */
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.CommentDefaultAccessModifier"})
class SingleContentTest {

  @ParameterizedTest
  @MethodSource("generateSingleContent")
  void getGrade(SingleChoiceTestQuestionContent content, Integer expectedGrade) {
    final Integer actualGrade = content.calculateGrade();

    Assertions.assertEquals(expectedGrade, actualGrade, "Should return correct grade");
  }

  static Stream<Arguments> generateSingleContent() {
    final String one = "one";
    final String two = "two";
    return Stream.of(
        Arguments.of(
            SingleChoiceTestQuestionContent.builder()
                .correctAnswer(one)
                .providedAnswer(one)
                .maxGrade(MAX_GRADE)
                .build(),
            100),
        Arguments.of(
            SingleChoiceTestQuestionContent.builder()
                .correctAnswer(two)
                .providedAnswer(one)
                .maxGrade(MAX_GRADE)
                .build(),
            0));
  }
}
