package ua.com.hiringservice.model.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import ua.com.hiringservice.config.JacksonConfiguration;
import ua.com.hiringservice.converter.ContentConverter;
import ua.com.hiringservice.exception.ContentTypeNotSpecifiedException;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.enums.exam.QuestionType;

/**
 * Common interface for all types of content.
 *
 * @apiNote This interface serves as the main interface for all types of content, providing a
 *     mechanism for marking content types using the {@link QuestionContentType} annotation. The
 *     annotation ensures proper serialization with JSON.
 * @implSpec Implementing classes must provide the required methods for obtaining the question
 *     description and provided answer.
 * @author Zakhar Kuropiatnyk
 * @since 06/01/2024
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "questionType",
    visible = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Content {

  /**
   * Retrieves the {@link QuestionContentType} annotation based on the specified {@link
   * QuestionType}. "questionType" is a "must-have" property for the {@link ObjectMapper}. It marks
   * different {@link Content} implementations to allow correct serialization and deserialization
   * processes during REST responses with the frontend. It is also used during the save and read
   * operations of {@link Question} and {@link Answer} entities in the database.
   *
   * <p>in close future need delete field type from {@link Question} - right now two types in one
   * Json - violates DRY principles.
   *
   * @see JacksonConfiguration
   * @see ContentConverter
   * @return The {@link QuestionType} associated with the specified question type.
   * @throws ContentTypeNotSpecifiedException If the content type is not specified using the {@link
   *     QuestionContentType} annotation.
   */
  @JsonProperty("questionType")
  default QuestionType getQuestionType() {
    final QuestionContentType contentTypeAnnotation =
        this.getClass().getAnnotation(QuestionContentType.class);
    if (contentTypeAnnotation != null) {
      return contentTypeAnnotation.value();
    } else {
      throw new ContentTypeNotSpecifiedException(this.getClass());
    }
  }

  /**
   * Retrieves the description of the question associated with the content.
   *
   * @return The question description.
   */
  Object getQuestionDescription();

  /**
   * Retrieves the provided answer associated with the content.
   *
   * @return The provided answer.
   */
  Object getProvidedAnswer();

  /**
   * Excludes the text property from a {@link TextImageWrapper} when it needs to be serialized with
   * {@code markdown=null}. anti copy security
   */
  void excludeHtmlFromTextImageWrapper();

  /**
   * Retrieves the {@link List} of all {@link TextImageWrapper} instanses with image=null.
   *
   * @return The {@link List<TextImageWrapper>}.
   */
  List<TextImageWrapper> collectAllTextImageWrappersWithImageNull();

  /**
   * Retrieves the safe updated {@link Content} safe check source {@link Content}. method need
   * update target including all Content usage logic. Resolving the problem of entity mapping -
   * preventing existing fields from being updated with a null value.
   *
   * @return The safe updated {@link Content}.
   */
  Content getSafeUpdatedContent(Content sourceContent);

  void setMaxGrade(Integer maxScore);

  Integer getMaxGrade();
}
