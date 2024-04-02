package ua.com.testsupport.datagenerator.contentdata;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.content.questioncontent.LongTextQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.ShortTextTestQuestionContent;

/**
 * Utility class providing JSON data and instances for testing {@link LongTextQuestionContent}.
 *
 * @implNote The JSON data includes a correct JSON representation and a modified version with an
 *     incorrect content contentType. The {@link #getJsonMatchInstance()} method creates an instance
 *     of {@link LongTextQuestionContent} for testing purposes.
 * @author Zakhar Kuropiatnyk
 */
public class LongTextQuestionContentData {

  private static final String JSON =
      """
                              {\
                              "questionDescription":{\
                              "html":"What is the capital of France?",\
                              "image":"paris_image_url.jpg"\
                              },\
                              "providedAnswer":"Paris",\
                              "maxGrade":100,\
                              "questionType":"COMPLEX_TEXT_TO_MENTOR"\
                              }""";

  private static final String JSON_TEXT_NULL =
      """
                             {\
                             "questionDescription":{\
                             "html":null,\
                             "image":"paris_image_url.jpg"\
                             },\
                             "providedAnswer":"Paris",\
                             "maxGrade":100,\
                             "questionType":"COMPLEX_TEXT_TO_MENTOR"\
                             }""";
  private static final String DEFAULT_ANSWER = "Paris";
  public static final String DEFAULT_QUESTION = "What is the capital of France?";
  public static final String DEFAULT_IMAGE = "paris_image_url.jpg";

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
    return JSON.replace("COMPLEX_TEXT_TO_MENTOR", "WRONG");
  }

  /**
   * Creates and returns an instance of {@link ShortTextTestQuestionContent} for testing purposes.
   *
   * @return An instance of {@link ShortTextTestQuestionContent}.
   */
  public static LongTextQuestionContent getJsonMatchInstance() {
    return LongTextQuestionContent.builder()
        .providedAnswer(DEFAULT_ANSWER)
        .questionDescription(
            TextImageWrapper.builder().html(DEFAULT_QUESTION).image(DEFAULT_IMAGE).build())
        .maxGrade(MAX_GRADE)
        .build();
  }
}
