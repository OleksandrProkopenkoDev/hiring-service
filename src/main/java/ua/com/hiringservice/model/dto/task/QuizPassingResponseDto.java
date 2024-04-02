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

/**
 * Data Transfer Object (DTO) representing the response for a quiz passing operation. It contains
 * fields representing Task with QUIZ type
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizPassingResponseDto {

  private UUID id;

  @Schema(description = "Enum quiz passing status", example = "ANSWERED")
  private PassingStatus status;

  @Schema(description = "Assessment of quiz passing", example = "8")
  private Integer score;

  @Schema(description = "Minimum score to successfully pass the quiz", example = "7")
  private Integer passingScore;

  @Schema(description = "Maximum number of points for this Task", example = "12")
  private Integer maxScore;

  @Schema(description = "Type of Task", example = "QUIZ")
  private TaskType type;

  @Schema(description = "Task id")
  private UUID taskId;

  @Schema(description = "List of answers provided by user")
  private List<AnswerDto> answers;
}
