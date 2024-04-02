package ua.com.testsupport.datagenerator.contentdata;

import java.util.ArrayList;
import java.util.List;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.questioncontent.ShortTextTestQuestionContent;

/**
 * Utility class providing JSON data and List of instances for testing {@link Content}.
 *
 * @implNote The JSON data includes a correct JSON representation and a modified version with an
 *     incorrect content type. The {@link #getListOfContentIntsnces()} method creates an List of
 *     instances of different {@link Content} for testing purposes.
 * @author Zakhar Kuropiatnyk
 */
public class ContentListData {

  /**
   * Creates and returns a List of instances of {@link Content} similar to List serialized in {@link
   * #getJsonForListOfContents()} for testing purposes.
   *
   * @return An instance of {@link ShortTextTestQuestionContent}.
   */
  public static List<Content> getListOfContentIntsnces() {
    final List<Content> contents = new ArrayList<>();
    contents.add(LongTextQuestionContentData.getJsonMatchInstance());
    contents.add(MatchingTestQuestionContentData.getJsonMatchInstance());
    contents.add(MultipleChoiceTestQuestionContentData.getJsonMatchInstance());
    contents.add(ShortTextTestQuestionContentData.getJsonMatchInstance());
    contents.add(SingleChoiceTestQuestionContentData.getJsonMatchInstance());
    contents.add(VoiceMediaQuestionContentData.getJsonMatchInstance());
    return contents;
  }

  /**
   * Create and returns the correct JSON representation of List of {@link Content} similar to {@link
   * #getJsonForListOfContents()} for testing.
   *
   * @return The correct JSON representation.
   */
  public static String getJsonForListOfContents() {
    return "["
        + LongTextQuestionContentData.getCorrectJson()
        + ","
        + MatchingTestQuestionContentData.getCorrectJson()
        + ","
        + MultipleChoiceTestQuestionContentData.getCorrectJson()
        + ","
        + ShortTextTestQuestionContentData.getCorrectJson()
        + ","
        + SingleChoiceTestQuestionContentData.getCorrectJson()
        + ","
        + VoiceMediaQuestionContentData.getCorrectJson()
        + "]";
  }

  public static String getJsonForListOfContentsWithTextNull() {
    return "["
        + LongTextQuestionContentData.getJsonTextNull()
        + ","
        + MatchingTestQuestionContentData.getJsonTextNull()
        + ","
        + MultipleChoiceTestQuestionContentData.getJsonTextNull()
        + ","
        + ShortTextTestQuestionContentData.getJsonTextNull()
        + ","
        + SingleChoiceTestQuestionContentData.getJsonTextNull()
        + ","
        + VoiceMediaQuestionContentData.getJsonTextNull()
        + "]";
  }
}
