package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/** Entity class representing a image for question. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "image")
public class HtmlQuestionContentImage {
  @Id private String id;
  private byte[] data;
}
