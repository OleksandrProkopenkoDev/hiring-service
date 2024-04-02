package ua.com.testsupport.datagenerator.contentdata;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import java.util.HashSet;
import java.util.Set;
import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.content.questioncontent.MultipleChoiceTestQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.ShortTextTestQuestionContent;

/**
 * Utility class providing JSON data and instances for testing {@link
 * MultipleChoiceTestQuestionContent}.
 *
 * @implNote The JSON data includes a correct JSON representation and a modified version with an
 *     incorrect content contentType. The {@link #getJsonMatchInstance()} method creates an instance
 *     of {@link MultipleChoiceTestQuestionContent} for testing purposes.
 * @author Zakhar Kuropiatnyk
 */
public class MultipleChoiceTestQuestionContentData {

  private static final String JSON =
      """
                     {\
                     "questionDescription":{\
                     "html":"What is the capital of France?",\
                     "image":"paris_image_url.jpg"\
                     }\
                     ,"answerOptions":[{\
                     "html":"Answer1",\
                     "image":"answer_image1_url.jpg"},\
                     {"html":"Answer2",\
                     "image":"answer_image1_url.jpg"}],\
                     "correctAnswer":["A","B"],\
                     "providedAnswer":["A","B"],\
                     "maxGrade":100,\
                     "questionType":"MULTIPLE_CHOICE_TO_APP"}""";

  private static final String JSON_TEXT_NULL =
      """
                     {\
                     "questionDescription":{\
                     "html":null,\
                     "image":"paris_image_url.jpg"\
                     },\
                     "answerOptions":[{\
                     "html":null,\
                     "image":"answer_image1_url.jpg"\
                     },{\
                     "html":null,\
                     "image":"answer_image1_url.jpg"\
                     }],\
                     "correctAnswer":["A","B"],\
                     "providedAnswer":["A","B"],\
                     "maxGrade":100,\
                     "questionType":"MULTIPLE_CHOICE_TO_APP"}""";

  /**
   * Returns the correct JSON representation for testing.
   *
   * @return The correct JSON representation.
   */
  public static String getCorrectJson() {
    return JSON;
  }

  public static String getJsonTextNull() {
    return JSON_TEXT_NULL;
  }

  /**
   * Returns modified JSON with an incorrect content contentType for testing.
   *
   * @return The modified JSON representation.
   */
  public static String getWrongContentTypeJson() {
    return JSON.replace("SIMPLE_TEXT_TO_APP", "WRONG");
  }

  /**
   * Creates and returns an instance of {@link ShortTextTestQuestionContent} for testing purposes.
   *
   * @return An instance of {@link ShortTextTestQuestionContent}.
   */
  public static MultipleChoiceTestQuestionContent getJsonMatchInstance() {
    final Set<TextImageWrapper> answerOptions = new HashSet<>();
    answerOptions.add(
        TextImageWrapper.builder().html("Answer1").image("answer_image1_url.jpg").build());
    answerOptions.add(
        TextImageWrapper.builder().html("Answer2").image("answer_image1_url.jpg").build());
    final Set<String> correctAnswer = new HashSet<>();
    correctAnswer.add("A");
    correctAnswer.add("B");
    final Set<String> providedAnswer = new HashSet<>(correctAnswer);

    return MultipleChoiceTestQuestionContent.builder()
        .questionDescription(
            TextImageWrapper.builder()
                .html("What is the capital of France?")
                .image("paris_image_url.jpg")
                .build())
        .answerOptions(answerOptions)
        .correctAnswer(correctAnswer)
        .providedAnswer(providedAnswer)
        .maxGrade(MAX_GRADE)
        .build();
  }
}
