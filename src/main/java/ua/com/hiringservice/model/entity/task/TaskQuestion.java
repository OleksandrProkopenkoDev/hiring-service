package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

/** Entity class representing the association between task and questions in the Quiz service. */
@Entity
@Table(name = "task_questions")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskQuestion {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  private Question question;

  private Integer weight;

  @Column(name = "index_in_task")
  private Integer indexInTask;
}
