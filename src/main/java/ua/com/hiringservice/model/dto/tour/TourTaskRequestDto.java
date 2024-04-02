package ua.com.hiringservice.model.dto.tour;

import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) representing a request to create or update a tour task. This DTO
 * encapsulates the necessary data, including the task ID, weight, and index within the tour.
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TourTaskRequestDto {

  private UUID taskId;

  @Positive private Integer weight;
}
