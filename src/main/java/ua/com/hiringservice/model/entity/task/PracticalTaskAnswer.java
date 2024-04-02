package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the history of passing practical tasks by users.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Entity
@Table(name = "practical_task_answer")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class PracticalTaskAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @JoinColumn(name = "task_passing_id")
  private TaskPassing taskPassing;

  @Column(name = "git_answer_link")
  private String gitAnswerLink;

  @Column(name = "max_score")
  private Integer maxScore;

  @Column(name = "comment")
  private String comment;
}
