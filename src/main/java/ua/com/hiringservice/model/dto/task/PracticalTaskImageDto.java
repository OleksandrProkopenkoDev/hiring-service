package ua.com.hiringservice.model.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a diagram associated with a practical task.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PracticalTaskImageDto {

  private String id;

  private byte[] bytes;

  public PracticalTaskImageDto(String id) {
    this.id = id;
  }
}
