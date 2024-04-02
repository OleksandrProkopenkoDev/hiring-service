package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.enums.PassingStatus;

/**
 * entity class for answer.
 *
 * @author Vladislav Sauliak, Zakhar Kuropiatnyk.
 */
@Entity
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "task_passing_id")
  private TaskPassing taskPassing;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private PassingStatus status;

  @Column(name = "score")
  private Integer score;

  @Column(name = "duration")
  private Long duration;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

  @Column(name = "weight")
  private Integer weight;

  @Column(name = "content", columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private Content content;

  @Column(name = "index_in_task")
  private Integer indexInTask;

  @Column(name = "response_duration")
  private Integer responseDuration;
}
