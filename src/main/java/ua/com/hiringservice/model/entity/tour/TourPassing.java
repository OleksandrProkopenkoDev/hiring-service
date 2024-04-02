package ua.com.hiringservice.model.entity.tour;

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
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.enums.tour.TourStatus;

/**
 * Represents the passage or completion of a tour by a user. This entity tracks the progress and
 * status of a user's interaction with a tour.
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tour_passing")
public class TourPassing {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "keycloak_user_id")
  private UUID keycloakUserId;

  @Enumerated(EnumType.STRING)
  private TourStatus status;

  @Default private Integer score = 0;

  @ManyToOne
  @JoinColumn(name = "tour_id", foreignKey = @ForeignKey(name = "TOUR_ID_FK"))
  private Tour tour;
}
