package ua.com.hiringservice.model.dto.tour;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** Data transfer object for Tour entity. */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {

  @Schema(description = "Tour UUID", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "Tour title.", example = "Java: Core")
  @NotBlank(message = "Tour title can not be empty")
  private String title;

  @Schema(
      description = "Tour description.",
      example = "This Tour is supposed to check Java: Core knowledge of the candidate.")
  private String description;

  @Schema(description = "List of tasks in current Tour.")
  @JsonProperty("tourTasks")
  private List<TourTaskDto> tourTaskDtos = new LinkedList<>();
}
