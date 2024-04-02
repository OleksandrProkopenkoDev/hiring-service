package ua.com.hiringservice.model.dto.tour;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.tour.TourStatus;

/** Data transfer object for TourPassing entity. */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TourPassingDto {

  private UUID id;

  private UUID keycloakUserId;

  @Schema(
      description =
          "Current state of an Tour passing."
              + "When tour is just created the state by default - NOT_STARTED.",
      example = "NOT_STARTED")
  private TourStatus status = TourStatus.NOT_STARTED;

  private Integer score = 0;

  @JsonProperty("tour")
  private TourDto tourDto;
}
