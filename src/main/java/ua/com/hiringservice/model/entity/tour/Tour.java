package ua.com.hiringservice.model.entity.tour;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.hiringservice.model.entity.task.Task;

/**
 * Entity that represent a Tour which users should pass to get grades. Tour includes the list of
 * {@link Task}s in it. By default, the {@link Task}s list is empty.
 *
 * <p>Tour mapped to {@link Task} entity by {@link OneToMany} relationship. In other words, one Tour
 * can have many {@link Task}s.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tour")
@Getter
@Setter
public class Tour {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Tour title can not be empty")
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @OneToMany(
      cascade = {CascadeType.ALL},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "tour_id")
  @OrderColumn(name = "index_in_tour")
  private Collection<TourTask> tourTasks = new LinkedHashSet<>();
}
