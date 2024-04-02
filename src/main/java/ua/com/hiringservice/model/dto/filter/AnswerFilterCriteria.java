package ua.com.hiringservice.model.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.QuestionType;

/**
 * Class with parameters for Answer Request Filtration
 *
 * @author Zakhar Kuropiatnyk
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerFilterCriteria {
  @Schema(
      description = "Enum question type",
      example = "SINGLE_CHOICE_TO_APP, MULTIPLE_CHOICE_TO_APP")
  private List<QuestionType> questionType;

  @Schema(description = "Enum passing type", example = "IN_PROGRESS, FINISHED")
  private List<PassingStatus> status;

  @Schema(description = "Min duration for question", example = "10")
  private Integer minDuration;

  @Schema(description = "Max duration for question", example = "50")
  private Integer maxDuration;

  @Schema(description = "QuizPassing Id UUID", example = "6eac0c17-db3a-4343-8b71-634a8a642b62")
  private List<UUID> quizPassingIds;

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  @AssertTrue(message = "Maximum duration cannot be less than minimum duration")
  private boolean isDurationRangeValid() {
    if (minDuration != null && maxDuration != null) {
      return maxDuration >= minDuration;
    }
    return true;
  }
}
