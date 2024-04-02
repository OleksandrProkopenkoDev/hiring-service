package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a practical task entity. Here we have a field with practical problem description. Also
 * we can add some illustrations to our description using List of diagrams stored in Mongo db.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Entity
@Table(name = "practical_task")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class PracticalTask {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "task_text")
  private String taskText;

  @ElementCollection
  @CollectionTable(
      name = "practical_task_image_ids",
      joinColumns = @JoinColumn(name = "practical_task_id"))
  @Column(name = "image_id")
  private List<String> imageIds = new ArrayList<>();
}
