package ua.com.hiringservice.model.entity.task;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an image file illustration to practical task.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "practical_task_image")
public class PracticalTaskImage {
  @Id private String id;

  private byte[] bytes;

  public PracticalTaskImage(byte[] bytes) {
    this.bytes = Arrays.copyOf(bytes, bytes.length);
  }
}
