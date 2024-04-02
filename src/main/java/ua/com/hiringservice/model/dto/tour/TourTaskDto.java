package ua.com.hiringservice.model.dto.tour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.dto.task.TaskDto;

/** Data transfer object for Tour Task entity. */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TourTaskDto {

  private UUID id;

  @JsonProperty("task")
  @NotNull
  private TaskDto taskDto;

  private Integer weight;

  private Integer indexInTour;
}
