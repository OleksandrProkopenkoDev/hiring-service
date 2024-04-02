package ua.com.hiringservice.model.entity.task;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.PassingStatus;

/** Replace this stub by correct Javadoc. */
@Entity
@Table(name = "task_passings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskPassing {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "keycloak_user_id")
  private UUID userKeycloakId;

  @Enumerated(EnumType.STRING)
  private PassingStatus status;

  @Default private Integer score = 0;

  @ManyToOne
  @JoinColumn(name = "task_id", foreignKey = @ForeignKey(name = "TASK_ID_FK"))
  private Task task;

  @OneToMany(mappedBy = "taskPassing", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Answer> answers = new ArrayList<>();

  @OneToOne(mappedBy = "taskPassing", cascade = CascadeType.ALL, orphanRemoval = true)
  private PracticalTaskAnswer practicalTaskAnswer;

  public void addAnswer(final Answer answer) {
    answers.add(answer);
    answer.setTaskPassing(this);
  }

  public void deleteAnswer(final Answer answer) {
    answers.remove(answer);
    answer.setTaskPassing(null);
  }
}
