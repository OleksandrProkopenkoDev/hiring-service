package ua.com.hiringservice.model.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a practical task. This dto contains diagrams as files (byte array) if they are
 * present.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PracticalTaskDto {

  private UUID id;

  @NotEmpty private String taskText;

  @JsonProperty("images")
  private List<PracticalTaskImageDto> imageDtos = new ArrayList<>();
}
