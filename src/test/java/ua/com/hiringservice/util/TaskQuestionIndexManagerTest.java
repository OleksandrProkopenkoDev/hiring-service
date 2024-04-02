package ua.com.hiringservice.util;

import static org.junit.jupiter.api.Assertions.*;
import static ua.com.hiringservice.util.TaskQuestionIndexManager.getNextIndex;
import static ua.com.hiringservice.util.TaskQuestionIndexManager.organizeIndexesIn;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTaskWithInvalidIndexes;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTaskWithValidIndexes;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTaskWithoutTaskQuestions;

import org.junit.jupiter.api.Test;
import ua.com.hiringservice.model.entity.task.Task;

/**
 * The TaskQuestionIndexManagerTest class contains test cases for the TaskQuestionIndexManager
 * utility class. It verifies the functionality of organizing indexes of task questions within a
 * task.
 */
class TaskQuestionIndexManagerTest {

  private static final String NEXT_INDEX_IN_TASK_GENERATED_INCORRECT =
      "Next indexInTask generated incorrect";

  @Test
  void organizeIndexesIn_shouldFillGap() {
    final Task taskActual = generateTaskWithInvalidIndexes();
    final Task taskExpected = generateTaskWithValidIndexes();
    organizeIndexesIn(taskActual);
    assertEquals(
        taskExpected,
        taskActual,
        "IndexInTask for actual taskQuestions are not corresponding to expected. "
            + "And they dont follow two rules: 1. Indexes must start with 1. "
            + "2. There are no gaps allowed between indexes");
  }

  @Test
  void methodGetNextIndex_shouldReturnCorrectIndex_whenTaskQuestionsAreOrganized() {
    final Task task = generateTaskWithValidIndexes();
    final Integer expected = 4;
    final Integer actual = getNextIndex(task);
    assertEquals(expected, actual, NEXT_INDEX_IN_TASK_GENERATED_INCORRECT);
  }

  @Test
  void methodGetNextIndex_shouldReturnCorrectIndex_whenTaskQuestionsIndexesHaveGap() {
    final Task task = generateTaskWithInvalidIndexes();
    final Integer expected = 4;
    final Integer actual = getNextIndex(task);
    assertEquals(expected, actual, NEXT_INDEX_IN_TASK_GENERATED_INCORRECT);
  }

  @Test
  void methodGetNextIndex_shouldReturnOne_whenTaskIsEmpty() {
    final Task task = generateTaskWithoutTaskQuestions();
    final Integer expected = 1;
    final Integer actual = getNextIndex(task);
    assertEquals(expected, actual, NEXT_INDEX_IN_TASK_GENERATED_INCORRECT);
  }
}
