package ua.com.hiringservice.model.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.TaskType;

/**
 * Data Transfer Object (DTO) representing the response for a practical task passing operation. It
 * contains fields representing Task with PRACTICAL_TASK type.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PracticalTaskPassingResponseDto {
  @Schema(description = "TaskPassing id")
  private UUID id;

  @Schema(description = "Enum practical task passing status", example = "IN_PROGRESS")
  private PassingStatus status;

  @Schema(description = "Assessment of practical task passing", example = "80")
  private Integer score;

  @Schema(description = "Minimum score to successfully pass the practical task", example = "70")
  private Integer passingScore;

  @Schema(description = "Maximum number of points for this Task", example = "100")
  private Integer maxScore;

  @Schema(description = "Type of Task", example = "PRACTICAL_TASK")
  private TaskType type;

  @Schema(description = "Task id")
  private UUID taskId;

  @Schema(description = "Answer on practical task that user provide")
  private ProvidePracticalTaskAnswerDto practicalTaskAnswer;

  @Schema(description = "Comment from reviewer")
  private String comment;
}
