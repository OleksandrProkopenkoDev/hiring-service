package ua.com.hiringservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Please, replace this stub with valid comment. */
@Embeddable
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class AuditEntity {

  @Column(name = "created_at", nullable = false, updatable = false)
  private ZonedDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private ZonedDateTime updatedAt;

  @PrePersist
  private void preCreate() {
    createdAt = ZonedDateTime.now();
    preUpdate();
  }

  @PreUpdate
  private void preUpdate() {
    updatedAt = ZonedDateTime.now();
  }
}
