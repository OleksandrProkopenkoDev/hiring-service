package ua.com.hiringservice.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import ua.com.hiringservice.config.JacksonConfiguration;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.questioncontent.LongTextQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.MatchingTestQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.MultipleChoiceTestQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.ShortTextTestQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.SingleChoiceTestQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.VoiceMediaQuestionContent;
import ua.com.testsupport.datagenerator.contentdata.ContentListData;
import ua.com.testsupport.datagenerator.contentdata.LongTextQuestionContentData;
import ua.com.testsupport.datagenerator.contentdata.MatchingTestQuestionContentData;
import ua.com.testsupport.datagenerator.contentdata.MultipleChoiceTestQuestionContentData;
import ua.com.testsupport.datagenerator.contentdata.ShortTextTestQuestionContentData;
import ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData;
import ua.com.testsupport.datagenerator.contentdata.TestDto;
import ua.com.testsupport.datagenerator.contentdata.VoiceMediaQuestionContentData;

/**
 * Unit tests for the Jackson configuration in {@link JacksonConfiguration}. Tests focus on the
 * serialization and deserialization different types of {@link Content} and DTOs(using {@link
 * TestDto}) using Jackson.
 *
 * @implNote The tests utilize {@code ua.com.datagenerator.content.question} package for generating
 *     correct and incorrect JSON strings, and instances for different implementations of {@link
 *     Content}.
 * @author Zakhar Kuropiatnyk
 */
@JsonTest
@Import({JacksonConfiguration.class, ObjectMapper.class})
class ContentJacksonConfigurationTest {
  public static final String EQUALS_TO_EXPECTED_STRING =
      "Returned json must be equals to: expected string";

  @Autowired private ObjectMapper objectMapper;

  @Test
  void readValue_shouldReturnShortTestQuestionContentInstance_providedCorrectJsonString()
      throws IOException {

    final String json = ShortTextTestQuestionContentData.getCorrectJson();

    final Content content = objectMapper.readValue(json, Content.class);

    assertEquals(
        ShortTextTestQuestionContent.class,
        content.getClass(),
        "Returned instance must be instance of ShortTextTestQuestionContent");
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedShortTextTestContentInstance()
      throws JsonProcessingException {
    final String expectedJson = ShortTextTestQuestionContentData.getCorrectJson();

    final ShortTextTestQuestionContent shortTextTestQuestionContent =
        ShortTextTestQuestionContentData.getJsonMatchInstance();
    final String actualJson = objectMapper.writeValueAsString(shortTextTestQuestionContent);
    assertEquals(expectedJson, actualJson, EQUALS_TO_EXPECTED_STRING);
  }

  @Test
  void readValue_shouldThrowInvalidTypeIdException_providedWrongJsonString() {

    final String wrongJson = ShortTextTestQuestionContentData.getWrongContentTypeJson();

    assertThrows(
        InvalidTypeIdException.class,
        () -> objectMapper.readValue(wrongJson, Content.class),
        """
                    Must throw InvalidTypeIdException because\
                     provided wrong value for property type in json""");
  }

  @Test
  void readValue_shouldReturnLongTestQuestionContentInstance_providedCorrectJsonString()
      throws IOException {

    final String json = LongTextQuestionContentData.getCorrectJson();

    final Content content = objectMapper.readValue(json, Content.class);

    assertEquals(
        LongTextQuestionContent.class,
        content.getClass(),
        "Returned instance must be instance of LongTextTestQuestionContent");
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedLongTextTestContentInstance()
      throws JsonProcessingException {
    final String expectedJson = LongTextQuestionContentData.getCorrectJson();

    final LongTextQuestionContent longTextQuestionContent =
        LongTextQuestionContentData.getJsonMatchInstance();
    final String actualJson = objectMapper.writeValueAsString(longTextQuestionContent);
    assertEquals(expectedJson, actualJson, EQUALS_TO_EXPECTED_STRING);
  }

  @Test
  void readValue_shouldReturnMatchingTestQuestionContentInstance_providedCorrectJsonString()
      throws IOException {

    final String json = MatchingTestQuestionContentData.getCorrectJson();

    final Content content = objectMapper.readValue(json, Content.class);

    assertEquals(
        MatchingTestQuestionContent.class,
        content.getClass(),
        "Returned instance must be instance of MatchingTestQuestionContent");
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedMatchingTestQuestionInstance()
      throws JsonProcessingException {
    final String expectedJson = MatchingTestQuestionContentData.getCorrectJson();

    final MatchingTestQuestionContent matchingTestQuestionContent =
        MatchingTestQuestionContentData.getJsonMatchInstance();
    final String actualJson = objectMapper.writeValueAsString(matchingTestQuestionContent);
    assertEquals(expectedJson, actualJson, EQUALS_TO_EXPECTED_STRING);
  }

  @Test
  void readValue_shouldReturnMultipleChoiceTestQuestionContentInstance_providedCorrectJsonString()
      throws IOException {

    final String json = MultipleChoiceTestQuestionContentData.getCorrectJson();

    final Content content = objectMapper.readValue(json, Content.class);

    assertEquals(
        MultipleChoiceTestQuestionContent.class,
        content.getClass(),
        "Returned instance must be instance of MultipleChoiceTestQuestionContent");
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedMultipleChoiceTestQuestionInstance()
      throws JsonProcessingException {
    final String expectedJson = MultipleChoiceTestQuestionContentData.getCorrectJson();

    final MultipleChoiceTestQuestionContent multipleChoiceTestQuestionContent =
        MultipleChoiceTestQuestionContentData.getJsonMatchInstance();
    final String actualJson = objectMapper.writeValueAsString(multipleChoiceTestQuestionContent);
    assertEquals(expectedJson, actualJson, EQUALS_TO_EXPECTED_STRING);
  }

  @Test
  void readValue_shouldReturnSingleChoiceTestQuestionContentInstance_providedCorrectJsonString()
      throws IOException {

    final String json = SingleChoiceTestQuestionContentData.getCorrectJson();

    final Content content = objectMapper.readValue(json, Content.class);

    assertEquals(
        SingleChoiceTestQuestionContent.class,
        content.getClass(),
        "Returned instance must be instance of SingleChoiceTestQuestionContent");
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedSingleChoiceTestInstance()
      throws JsonProcessingException {
    final String expectedJson = SingleChoiceTestQuestionContentData.getCorrectJson();

    final SingleChoiceTestQuestionContent singleChoiceTestQuestionContent =
        SingleChoiceTestQuestionContentData.getJsonMatchInstance();
    final String actualJson = objectMapper.writeValueAsString(singleChoiceTestQuestionContent);
    assertEquals(expectedJson, actualJson, EQUALS_TO_EXPECTED_STRING);
  }

  @Test
  void readValue_shouldReturnVoiceMediaQuestionContentInstance_providedCorrectJsonString()
      throws IOException {

    final String json = VoiceMediaQuestionContentData.getCorrectJson();

    final Content content = objectMapper.readValue(json, Content.class);

    assertEquals(
        VoiceMediaQuestionContent.class,
        content.getClass(),
        "Returned instance must be instance of VoiceMediaQuestionContent");
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedVoiceMediaQuestionContentInstance()
      throws JsonProcessingException {
    final String expectedJson = VoiceMediaQuestionContentData.getCorrectJson();

    final VoiceMediaQuestionContent voiceMediaQuestionContent =
        VoiceMediaQuestionContentData.getJsonMatchInstance();
    final String actualJson = objectMapper.writeValueAsString(voiceMediaQuestionContent);
    assertEquals(expectedJson, actualJson, EQUALS_TO_EXPECTED_STRING);
  }

  @Test
  void readValue_shouldReturnListOfContentsSimilarToExpectedList_providedCorrectJsonString()
      throws IOException {

    final String json = ContentListData.getJsonForListOfContents();

    final List<Content> expectedList = ContentListData.getListOfContentIntsnces();
    final Object expectedProvidedAnswer = expectedList.get(0).getProvidedAnswer();

    final List<Content> actualList =
        objectMapper.readValue(json, new TypeReference<List<Content>>() {});
    final Object actualProvidedAnswer = actualList.get(0).getProvidedAnswer();

    assertFalse(actualList.isEmpty(), "Deserealized List<Content must not be empty>");

    assertEquals(
        expectedList.get(0),
        actualList.get(0),
        """
                              First element from expected List \
                              have to be equals to the first element \
                              from actualList(deserialized) List""");

    assertEquals(
        expectedProvidedAnswer,
        actualProvidedAnswer,
        """
                  First element's provided answer from expected List \
                  have to be equals to the first element's provided answer \
                  from actualList(deserialized) List""");
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedListOfContent()
      throws JsonProcessingException {
    final String expectedJson = ContentListData.getJsonForListOfContents();

    final List<Content> contents = ContentListData.getListOfContentIntsnces();
    final String actualJson = objectMapper.writeValueAsString(contents);
    assertEquals(expectedJson, actualJson, EQUALS_TO_EXPECTED_STRING);
  }

  @Test
  void writeValueAsString_shouldReturnCorrectJsonString_providedTestDtoInstance()
      throws JsonProcessingException {
    final String expected = "{\"username\":\"test\",\"password\":\"testpassword\"}";

    final TestDto testDto = TestDto.builder().username("test").password("testpassword").build();

    final String actual = objectMapper.writeValueAsString(testDto);
    assertEquals(expected, actual, "Actual json string should be equals to expected");
  }

  @Test
  void readValue_shouldReturnTestDtoInstance_providedCorrectJsonString() throws IOException {

    final String json = "{\"username\":\"test\",\"password\":\"testpassword\"}";

    final TestDto expected = TestDto.builder().username("test").password("testpassword").build();

    final TestDto actual = objectMapper.readValue(json, TestDto.class);

    assertEquals(expected, actual, "Actual testDto Instance should be equals to expected");
  }
}
