package ua.com.testsupport.datagenerator.contentdata;

import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.MAX_GRADE;

import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.content.questioncontent.ShortTextTestQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.VoiceMediaQuestionContent;

/**
 * Utility class providing JSON data and instances for testing {@link VoiceMediaQuestionContent}.
 *
 * @implNote The JSON data includes a correct JSON representation and a modified version with an
 *     incorrect content contentType. The {@link #getJsonMatchInstance()} method creates an instance
 *     of {@link VoiceMediaQuestionContent} for testing purposes.
 * @author Zakhar Kuropiatnyk
 */
public class VoiceMediaQuestionContentData {
  private static final String JSON =
      """
         {\
         "questionDescription":{"html":"What is the capital of France?",\
         "image":"paris_image_url.jpg"},\
         "providedAnswer":"bA==",\
         "maxGrade":100,\
         "questionType":"VOICE_TO_MENTOR"}""";
  private static final String JSON_TEXT_NULL =
      """
         {\
         "questionDescription":{\
         "html":null,\
         "image":"paris_image_url.jpg"\
         },\
         "providedAnswer":"bA==",\
         "maxGrade":100,\
         "questionType":"VOICE_TO_MENTOR"\
         }""";

  /**
   * Returns the correct JSON representation for testing.
   *
   * @return The correct JSON representation.
   */
  public static String getCorrectJson() {
    return JSON;
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
  public static VoiceMediaQuestionContent getJsonMatchInstance() {

    return VoiceMediaQuestionContent.builder()
        .questionDescription(
            TextImageWrapper.builder()
                .html("What is the capital of France?")
                .image("paris_image_url.jpg")
                .build())
        .providedAnswer(new byte[] {0x6C})
        .maxGrade(MAX_GRADE)
        .build();
  }

  public static String getJsonTextNull() {
    return JSON_TEXT_NULL;
  }
}
