package ua.com.hiringservice.model.entity.tour;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.entity.task.Task;

/** Replace this stub by correct Javadoc. */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tour_task")
public class TourTask {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "task_id", referencedColumnName = "id")
  private Task task;

  private Integer weight;

  @Column(name = "index_in_tour")
  private Integer indexInTour;
}
