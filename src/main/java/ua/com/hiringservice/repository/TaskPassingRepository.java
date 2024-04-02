package ua.com.hiringservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;

/** Replace this stub by correct Javadoc. */
@Repository
public interface TaskPassingRepository extends JpaRepository<TaskPassing, UUID> {

  @Query(
      """
          SELECT CASE WHEN COUNT(qp) > 0 THEN TRUE ELSE FALSE END FROM TaskPassing qp
          WHERE qp.userKeycloakId = :keycloakId AND qp.status =
          ua.com.hiringservice.model.enums.PassingStatus.IN_PROGRESS""")
  boolean isUserPassTaskNow(@Param("keycloakId") UUID keycloakId);

  Optional<TaskPassing> findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
      UUID quizId, UUID userKeycloakId, PassingStatus status);

  Page<TaskPassing> findAllByUserKeycloakIdAndStatus(
      UUID userKeycloakId, PassingStatus passingStatus, Pageable pageable);

  Page<TaskPassing> findByUserKeycloakIdAndStatusAndTaskId(
      UUID userKeycloakId, PassingStatus passingStatus, UUID taskId, Pageable pageable);

  List<TaskPassing> findAllByTaskId(UUID taskId);

  List<TaskPassing> findAllByTaskIdAndUserKeycloakId(UUID taskId, UUID userId);
}
