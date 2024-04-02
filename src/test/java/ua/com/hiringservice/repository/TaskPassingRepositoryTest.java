package ua.com.hiringservice.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ua.com.testsupport.constant.TestDataConstant.QUESTIONS_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_PASSING_REPOSITORY_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_QUESTIONS_DATA_SQL;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateUserIdWithFinishedTaskPassing;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateUserIdWithGradedTaskPassing;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateUserIdWithInProgressTaskPassing;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateExistingTask3Id;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ua.com.hiringservice.IntegrationTestBase;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;

/** Replace this stub by correct Javadoc. */
@Sql(
    scripts = {
      QUESTIONS_DATA_SQL,
      TASK_DATA_SQL,
      TASK_QUESTIONS_DATA_SQL,
      TASK_PASSING_REPOSITORY_SQL
    })
class TaskPassingRepositoryTest extends IntegrationTestBase {

  @Autowired private TaskPassingRepository taskPassingRepository;

  @Test
  void isUserPassQuizNow_shouldReturnFalse_whenUserDontHaveInProgresQuizPassings() {
    final UUID userId = generateUserIdWithFinishedTaskPassing();

    final boolean actual = taskPassingRepository.isUserPassTaskNow(userId);

    assertFalse(actual, "User should not have in-progress quiz passings");
  }

  @Test
  void isUserPassQuizNow_shouldReturnTrue_whenUserHaveInProgresQuizPassings() {
    final UUID userId = generateUserIdWithInProgressTaskPassing();

    final boolean actual = taskPassingRepository.isUserPassTaskNow(userId);

    assertTrue(actual, "User should have in-progress quiz passings");
  }

  @Test
  void findByUserKeycloakIdAndStatusAndTask_shouldReturnTaskDto_whenTaskIsPassed() {
    final UUID userId = generateUserIdWithGradedTaskPassing();
    final UUID taskId = generateExistingTask3Id();
    final PassingStatus passingStatus = PassingStatus.GRADED;
    final Pageable pageable = Pageable.unpaged();
    final Page<TaskPassing> taskPassing =
        taskPassingRepository.findByUserKeycloakIdAndStatusAndTaskId(
            userId, passingStatus, taskId, pageable);
    assertFalse(taskPassing.isEmpty(), "Task passing was not found");
  }
}
