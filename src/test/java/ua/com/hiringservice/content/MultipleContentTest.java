package ua.com.hiringservice.content;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.com.hiringservice.model.content.questioncontent.MultipleChoiceTestQuestionContent;

/**
 * Test class which tests Multiple Content methods
 *
 * @author Vladislav Sauliak
 */
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.CommentDefaultAccessModifier"})
class MultipleContentTest {

  @ParameterizedTest
  @MethodSource("generateMultipleContent")
  void getGrade(MultipleChoiceTestQuestionContent content, Integer expectedGrade) {
    final Integer actualGrade = content.calculateGrade();

    Assertions.assertEquals(expectedGrade, actualGrade, "Should return correct grade");
  }

  static Stream<Arguments> generateMultipleContent() {
    final String one = "one";
    final String two = "two";
    final String three = "three";
    return Stream.of(
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one, two))
                .providedAnswer(Set.of(three, one))
                .maxGrade(MAX_GRADE)
                .build(),
            50),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one, two))
                .providedAnswer(Set.of(one, two, three))
                .maxGrade(MAX_GRADE)
                .build(),
            50),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one, two))
                .providedAnswer(Set.of(one))
                .maxGrade(MAX_GRADE)
                .build(),
            50),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one, two))
                .providedAnswer(Set.of(one, two))
                .maxGrade(MAX_GRADE)
                .build(),
            100),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one, two, three))
                .providedAnswer(Set.of(one, two))
                .maxGrade(MAX_GRADE)
                .build(),
            67),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one))
                .providedAnswer(Set.of(two))
                .maxGrade(MAX_GRADE)
                .build(),
            0),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one))
                .providedAnswer(Set.of(one))
                .maxGrade(MAX_GRADE)
                .build(),
            100),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one, two))
                .providedAnswer(Set.of(one, two, "five", "six"))
                .maxGrade(MAX_GRADE)
                .build(),
            0),
        Arguments.of(
            MultipleChoiceTestQuestionContent.builder()
                .correctAnswer(Set.of(one, two, three))
                .providedAnswer(Set.of(one, two, three, "five", "six"))
                .maxGrade(MAX_GRADE)
                .build(),
            33));
  }
}
