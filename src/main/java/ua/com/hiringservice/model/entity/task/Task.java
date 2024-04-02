package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.entity.AuditEntity;
import ua.com.hiringservice.model.enums.exam.TaskType;

/**
 * Entity class representing a task in the task service.
 *
 * <p>This entity is mapped to the "tasks" table in the database.
 *
 * <p>The task entity has a one-to-many relationship with {@link TaskQuestion}s, which represent the
 * association between tasks and questions. The relationship is defined with {@link OneToMany} and
 * {@link JoinColumn} annotations.
 */
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;

  @Column(name = "description")
  private String descriptionHtml;

  @Enumerated(EnumType.STRING)
  private TaskType type;

  @OneToMany(
      cascade = {CascadeType.ALL},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "task_id")
  private List<TaskQuestion> taskQuestions = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "practical_task_id")
  private PracticalTask practicalTask;

  @Column(name = "passing_score")
  private Integer passingScore;

  @Column(name = "max_score")
  private Integer maxScore;

  @Column(name = "total_question")
  private Integer totalQuestion;

  @Column(name = "total_duration")
  private Long totalDuration;

  @Column(name = "total_weight")
  private Integer totalWeight;

  @Embedded private AuditEntity auditEntity;

  private Boolean published = false;

  @Column(name = "published_at")
  private ZonedDateTime publishedAt;

  private String comment;

  /**
   * Recalculates the derived fields 'totalQuestion' and 'totalDuration' based on the current state
   * of the 'taskQuestions' list. This method is annotated with {@link
   * jakarta.persistence.PrePersist} and {@link jakarta.persistence.PreUpdate}, ensuring it is
   * executed before entity persistence and updates.
   *
   * <p>The 'totalQuestion' field is updated with the total number of task questions in the
   * 'taskQuestions' list. The 'totalDuration' field is calculated by summing up the durations of
   * individual task questions.
   *
   * <p>Note: Ensure that the 'taskQuestions' list is not null before invoking this method.
   */
  @PrePersist
  @PreUpdate
  public void calculateDerivedFields() {
    // Calculate totalQuestion and totalDuration based on taskQuestions list
    if (taskQuestions != null) {
      totalQuestion = taskQuestions.size();
      totalDuration = calculateTotalDuration();
      totalWeight = calculateTotalWeight();
    }
  }

  private Integer calculateTotalWeight() {
    return taskQuestions.stream().mapToInt(TaskQuestion::getWeight).sum();
  }

  private Long calculateTotalDuration() {
    return taskQuestions.stream()
        .mapToLong(taskQuestion -> taskQuestion.getQuestion().getDuration())
        .sum();
  }
}
