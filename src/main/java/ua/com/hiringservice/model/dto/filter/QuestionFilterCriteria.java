package ua.com.hiringservice.model.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.exam.QuestionType;

/**
 * Data Transfer Object (DTO) representing criteria for filtering questions.
 *
 * <p>The class includes fields to filter questions based on type, minimum duration, and maximum
 * duration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionFilterCriteria {
  @Schema(
      description = "Enum question type",
      example = "SINGLE_CHOICE_TO_APP, MULTIPLE_CHOICE_TO_APP")
  private List<QuestionType> questionType;

  @Schema(description = "Min duration for question", example = "10")
  private Long minDuration;

  @Schema(description = "Max duration for question", example = "50")
  private Long maxDuration;

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  @AssertTrue(message = "Maximum duration cannot be less than minimum duration")
  private boolean isDurationRangeValid() {
    if (minDuration != null && maxDuration != null) {
      return maxDuration >= minDuration;
    }
    return true;
  }
}
