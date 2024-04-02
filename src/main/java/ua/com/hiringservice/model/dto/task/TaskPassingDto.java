package ua.com.hiringservice.model.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.TaskType;

/** Data Transfer Object (DTO) representing the response for a Task passing operation. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskPassingDto {

  private UUID id;

  @Schema(description = "Enum task passing status", example = "GRADED")
  private PassingStatus status;

  @Schema(description = "Assessment of task passing", example = "4")
  private Integer score;

  @Schema(description = "Minimum score to successfully pass the quiz", example = "3")
  private Integer passingScore;

  @Schema(description = "Maximum number of points for this Task", example = "5")
  private Integer maxScore;

  private TaskType type;

  @Schema(description = "From this quiz is made quiz passing")
  private TaskDto task;

  @Schema(description = "List of answers that users should give")
  private List<AnswerDto> answers;

  @Schema(description = "Answer on practical task that user provide")
  private ProvidePracticalTaskAnswerDto practicalTaskAnswer;

  @Schema(description = "Comment from reviewer")
  private String comment;
}
