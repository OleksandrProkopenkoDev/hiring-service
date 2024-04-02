package ua.com.hiringservice.model.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.content.Content;

/**
 * Data Transfer Object (DTO) representing information about a question.
 *
 * <p>The class includes essential details such as ID, type, description, duration, and content. It
 * is used for communication between the frontend and backend to transfer question-related data.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class QuestionDto {

  private UUID id;

  @Schema(description = "Question description", example = "This question about bla-bla-bla")
  private String comment;

  @Schema(
      description = "Question duration in second",
      example = "20",
      requiredMode = RequiredMode.REQUIRED)
  @Positive
  @NotNull
  private Long duration;

  @NotNull
  @Schema(
      description = """
              Content like a body, answer options etc.""",
      requiredMode = RequiredMode.REQUIRED,
      example =
          """
              {
                "questionDescription": {
                  "markdown": "What is the capital of France?",
                  "image": "imageId"
                },
                "answerOptions": [
                  {
                    "markdown": "Answer1",
                    "image": "imageId"
                  },
                  {
                    "markdown": "Answer2",
                    "image": "imageId"
                  }
                ],
                "correctAnswer": "Answer1",
                "providedAnswer": "Answer1",
                "questionType": "SINGLE_CHOICE_TO_APP"
              }""")
  private Content content;
}
