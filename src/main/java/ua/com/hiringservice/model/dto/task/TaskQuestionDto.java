package ua.com.hiringservice.model.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing quiz question information. Contains details such as
 * the unique identifier, associated question, weight, and sequence in the quiz.
 *
 * @author Oleksandr Prokopenko
 * @version 1.0
 * @since 2024-01-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskQuestionDto {

  private UUID id;

  @NotNull
  @JsonProperty("question")
  private QuestionDto questionDto;

  @NotNull(message = "Weight must not be null")
  @Positive(message = "Weight must be a positive integer greater than zero")
  private Integer weight;

  @Positive(message = "Index must be a positive integer greater than zero")
  private Integer indexInTask;
}
