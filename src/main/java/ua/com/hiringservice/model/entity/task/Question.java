package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ua.com.hiringservice.model.content.Content;

/**
 * Entity class representing a question in the quiz system.
 *
 * <p>The class includes details such as ID, description, type, duration, and content.
 */
@Entity
@Table(name = "questions")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String comment;

  @Column(name = "duration")
  private Long duration;

  @Column(name = "content", columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private Content content;
}
