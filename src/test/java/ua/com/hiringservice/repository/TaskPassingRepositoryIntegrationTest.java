package ua.com.hiringservice.repository;

import static org.junit.jupiter.api.Assertions.*;
import static ua.com.testsupport.constant.TestDataConstant.QUESTIONS_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_DATA_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_PASSING_REPOSITORY_SQL;
import static ua.com.testsupport.constant.TestDataConstant.TASK_QUESTIONS_DATA_SQL;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateUserIdWithFinishedTaskPassing;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateUserIdWithInProgressTaskPassing;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ua.com.hiringservice.IntegrationTestBase;

/** Replace this stub by correct Javadoc. */
@Sql(
    scripts = {
      QUESTIONS_DATA_SQL,
      TASK_DATA_SQL,
      TASK_QUESTIONS_DATA_SQL,
      TASK_PASSING_REPOSITORY_SQL
    })
class TaskPassingRepositoryIntegrationTest extends IntegrationTestBase {

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
}
