package ua.com.hiringservice.model.dto.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a practical task. It contains taskText as image, and it contains
 * practicalTaskAnswerId - this is an id of pre saved answer object with status but without provided
 * answer.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PracticalTaskWithBytesDto {

  private UUID practicalTaskAnswerId;

  private byte[] taskTextImage;

  private List<PracticalTaskImageDto> images = new ArrayList<>();
}
