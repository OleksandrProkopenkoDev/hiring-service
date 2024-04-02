package ua.com.hiringservice.model.dto.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a practical task answer provided by applicant.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvidePracticalTaskAnswerDto {

  @NotEmpty
  @Size(max = 255)
  private String gitAnswerLink;

  @Size(max = 255)
  private String comment;
}
