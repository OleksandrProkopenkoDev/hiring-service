package ua.com.testsupport.datagenerator.contentdata;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.content.questioncontent.ShortTextTestQuestionContent;

/**
 * Utility class providing JSON data and instances for testing {@link ShortTextTestQuestionContent}.
 *
 * @implNote The JSON data includes a correct JSON representation and a modified version with an
 *     incorrect content contentType. The {@link #getJsonMatchInstance()} method creates an instance
 *     of {@link ShortTextTestQuestionContent} for testing purposes.
 * @author Zakhar Kuropiatnyk
 */
public class ShortTextTestQuestionContentData {

  private static final String JSON =
      """
                  {\
                  "questionDescription":{\
                  "html":"What is the capital of France?",\
                  "image":"paris_image_url.jpg"\
                  },\
                  "correctAnswer":"Paris",\
                  "providedAnswer":"Paris",\
                  "maxGrade":100,\
                  "questionType":"SIMPLE_TEXT_TO_APP"\
                  }""";
  private static final String JSON_TEXT_NULL =
      """
                  {\
                  "questionDescription":{\
                  "html":null,\
                  "image":"paris_image_url.jpg"\
                  },\
                  "correctAnswer":"Paris",\
                  "providedAnswer":"Paris",\
                  "maxGrade":100,\
                  "questionType":"SIMPLE_TEXT_TO_APP"\
                  }""";

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
  public static ShortTextTestQuestionContent getJsonMatchInstance() {
    return ShortTextTestQuestionContent.builder()
        .correctAnswer("Paris")
        .providedAnswer("Paris")
        .questionDescription(
            TextImageWrapper.builder()
                .html("What is the capital of France?")
                .image("paris_image_url.jpg")
                .build())
        .maxGrade(MAX_GRADE)
        .build();
  }
}
