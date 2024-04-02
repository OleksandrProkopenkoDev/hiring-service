package ua.com.hiringservice.model.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.exam.TaskType;

/**
 * Data Transfer Object (DTO) for representing quiz information. Contains details such as the unique
 * identifier, title, description, passing score, and a list of associated quiz questions.
 *
 * @author Oleksandr Prokopenko
 * @version 1.2
 * @since 2024-01-04
 */
@SuppressWarnings("PMD.TooManyFields")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

  private UUID id;

  @Size(min = 2, message = "Title must have at least two symbols")
  @Pattern(regexp = "^[a-zA-Z].*", message = "Title must start with a letter")
  @NotNull
  private String title;

  @Size(min = 10, message = "Description must have at least 10 symbols")
  private String descriptionHtml;

  @Min(value = 0, message = "Passing score must be greater than or equal to 0")
  private Integer passingScore;

  @Positive private Integer maxScore;

  private TaskType type;

  @JsonProperty("totalQuestions")
  private Integer totalQuestion;

  private Long totalDuration;

  private Integer totalWeight;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ZonedDateTime createdAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ZonedDateTime updatedAt;

  private Boolean published = false;

  @JsonInclude(Include.NON_NULL)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ZonedDateTime publishedAt;

  private String comment;

  @JsonProperty("taskQuestions")
  private List<TaskQuestionDto> taskQuestionDtos = new ArrayList<>();

  @JsonProperty("practicalTask")
  private PracticalTaskDto practicalTaskDto;
}
