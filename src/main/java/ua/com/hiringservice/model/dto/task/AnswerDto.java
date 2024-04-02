package ua.com.hiringservice.model.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.enums.PassingStatus;

/**
 * Dto representation Answer entity.
 *
 * @author Zakhar Kuropiatnyk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDto {

  private UUID id;

  @Schema(
      description = "Enum passing type",
      example = "IN_PROGRESS",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonIgnore
  private PassingStatus status;

  private Integer score;

  @Schema(
      description = "Max allowed answering duration in second",
      example = "20",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @Positive
  @NotNull
  private Long duration;

  @Schema(description = "comment from exam Reviewer")
  private String comment;

  @NotNull
  @Schema(
      description = "Question content like a body, answer options etc",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private Content content;

  @Schema(description = "Number of question inside quiz ", example = "2")
  private Integer indexInTask;

  @JsonIgnore private Integer weight;

  // todo wait for better time
  //  @Schema(
  //      description = "How long user provided answer",
  //      example = "20",
  //      requiredMode = Schema.RequiredMode.REQUIRED)
  //  @Positive
  //  @NotNull
  //  @JsonProperty("responceDuration")
  //  private Integer responseDuration;
}
