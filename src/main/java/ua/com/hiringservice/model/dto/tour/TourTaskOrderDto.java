package ua.com.hiringservice.model.dto.tour;

import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data transfer object for Tour entity. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourTaskOrderDto {
  private UUID tourTaskId;

  @Positive private Integer indexInTour;
}
