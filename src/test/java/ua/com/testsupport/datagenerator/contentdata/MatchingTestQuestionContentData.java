package ua.com.testsupport.datagenerator.contentdata;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.content.questioncontent.MatchingTestQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.ShortTextTestQuestionContent;

/**
 * Utility class providing JSON data and instances for testing {@link MatchingTestQuestionContent}.
 *
 * @implNote The JSON data includes a correct JSON representation and a modified version with an
 *     incorrect content contentType. The {@link #getJsonMatchInstance()} method creates an instance
 *     of {@link MatchingTestQuestionContent} for testing purposes.
 * @author Zakhar Kuropiatnyk
 */
public class MatchingTestQuestionContentData {

  private static final String JSON =
      """
                     {\
                     "questionDescription":{"html":"What is the capital of France?",\
                     "image":"paris_image_url.jpg"},\
                     "answerOptions":\
                     {"TextImageWrapper(html=Answer1, image=answer_image1_url.jpg)":\
                     {"html":"Maybe capital of France is Paris?","image":"paris_image_url.jpg"},\
                     "TextImageWrapper(html=Answer2, image=answer_image1_url.jpg)":\
                     {"html":"Maybe capital of France is?","image":"image_url.jpg"}},\
                     "correctAnswer":{"A":"Paris","B":"Pari"},\
                     "providedAnswer":{"A":"Paris","B":"Pari"},\
                     "maxGrade":100,\
                     "questionType":"MATCHING_TO_APP"}""";

  private static final String JSON_TEXT_NULL =
      """
                     {\
                     "questionDescription":{\
                     "html":null,\
                     "image":"paris_image_url.jpg"\
                     },\
                     "answerOptions":{\
                     "TextImageWrapper(html=null, image=answer_image1_url.jpg)"\
                     :{\
                     "html":null,\
                     "image":"paris_image_url.jpg"},\
                     "TextImageWrapper(html=null, image=answer_image1_url.jpg)"\
                     :{"html":null,"image":"image_url.jpg"}\
                     },\
                     "correctAnswer":\
                     {"A":"Paris","B":"Pari"},\
                     "providedAnswer":{"A":"Paris","B":"Pari"},\
                     "maxGrade":100,\
                     "questionType":"MATCHING_TO_APP"}""";

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
  public static MatchingTestQuestionContent getJsonMatchInstance() {
    final Map<TextImageWrapper, TextImageWrapper> answerOptions = new ConcurrentHashMap<>();
    answerOptions.put(
        TextImageWrapper.builder().html("Answer1").image("answer_image1_url.jpg").build(),
        TextImageWrapper.builder()
            .html("Maybe capital of France is Paris?")
            .image("paris_image_url.jpg")
            .build());
    answerOptions.put(
        TextImageWrapper.builder().html("Answer2").image("answer_image1_url.jpg").build(),
        TextImageWrapper.builder()
            .html("Maybe capital of France is?")
            .image("image_url.jpg")
            .build());
    final Map<String, String> correctAnswer = new ConcurrentHashMap<>();
    correctAnswer.put("A", "Paris");
    correctAnswer.put("B", "Pari");
    final Map<String, String> providedAnswer = new ConcurrentHashMap<>(correctAnswer);

    return MatchingTestQuestionContent.builder()
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
