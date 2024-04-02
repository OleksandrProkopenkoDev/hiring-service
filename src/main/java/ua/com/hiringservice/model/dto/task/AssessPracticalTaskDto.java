package ua.com.hiringservice.model.dto.task;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing an assessment request body from reviewer when practical task answer is manually
 * assessed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessPracticalTaskDto {

  @Min(value = 0, message = "Score must be at least 0")
  @Max(value = 100, message = "Score must be at most 100")
  private Integer score;

  @Size(max = 255)
  private String comment;
}
