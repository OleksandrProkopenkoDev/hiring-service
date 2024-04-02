package ua.com.testsupport.datagenerator.task;

import java.util.List;
import java.util.UUID;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;

/**
 * This class provides methods to generate test data for practical tasks, including images and DTOs.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public final class PracticalTaskDataGenerator {

  private static final String IMAGE1_ID = "65fa15813ee47c0987d0d0e1";
  private static final String IMAGE2_ID = "65fa15813ee47c0987d0d0e2";
  private static final String IMAGE3_ID = "65fa15813ee47c0987d0d0e3";

  private static final byte[] IMAGE1_BYTES = "YXNkZmFzZGY=".getBytes();
  private static final byte[] IMAGE2_BYTES = "aGVsbG8gd29ybGQ=".getBytes();
  private static final byte[] IMAGE3_BYTES = "aGVsbG8gd29ybGQgMiBkcyBzZA==".getBytes();

  private static final UUID PRACTICAL_TASK1_ID =
      UUID.fromString("30a87862-cf6d-44f4-ad80-827d1e73d9b1");
  private static final UUID PRACTICAL_TASK2_ID =
      UUID.fromString("30a87862-cf6d-44f4-ad80-827d1e73d9b2");
  private static final UUID PRACTICAL_TASK3_ID =
      UUID.fromString("30a87862-cf6d-44f4-ad80-827d1e73d9b3");
  private static final String TASK_TEXT =
      "You are given two non-empty linked lists representing two non-negative integers. "
          + "The digits are stored in reverse order, and each of their nodes contains "
          + "a single digit. Add the two numbers and return the sum as a linked list. "
          + "You may assume the two numbers do not contain any leading zero, "
          + "except the number 0 itself. Example 1: Input: l1 = [2,4,3], "
          + "l2 = [5,6,4]Output: [7,0,8] Explanation: 342 + 465 = 807. "
          + "Example 2: Input: l1 = [0], l2 = [0] Output: [0]"
          + "Example 3: Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9] "
          + "Output: [8,9,9,9,0,0,0,1] Constraints: The number of nodes in each linked list "
          + "is in the range [1, 100]. 0 &lt;= Node.val &lt;= 9 It is guaranteed that"
          + " the list represents a number that does not have leading zeros. ";

  private PracticalTaskDataGenerator() {
    throw new UnsupportedOperationException(
        "Utility PracticalTaskDataGenerator class cannot be instantiated");
  }

  public static List<String> generateImageIds() {
    return List.of(IMAGE1_ID, IMAGE2_ID, IMAGE3_ID);
  }

  public static List<PracticalTaskImage> generateImages() {
    return List.of(
        PracticalTaskImage.builder().id(IMAGE1_ID).bytes(IMAGE1_BYTES).build(),
        PracticalTaskImage.builder().id(IMAGE2_ID).bytes(IMAGE2_BYTES).build(),
        PracticalTaskImage.builder().id(IMAGE3_ID).bytes(IMAGE3_BYTES).build());
  }

  public static List<PracticalTaskImageDto> generateImageDtos() {
    return List.of(
        PracticalTaskImageDto.builder().id(IMAGE1_ID).bytes(IMAGE1_BYTES).build(),
        PracticalTaskImageDto.builder().id(IMAGE2_ID).bytes(IMAGE2_BYTES).build(),
        PracticalTaskImageDto.builder().id(IMAGE3_ID).bytes(IMAGE3_BYTES).build());
  }

  public static PracticalTask generatePracticalTask() {
    return PracticalTask.builder()
        .id(PRACTICAL_TASK1_ID)
        .taskText(TASK_TEXT)
        .imageIds(generateImageIds())
        .build();
  }

  public static UUID generatePracticalTaskId() {
    return PRACTICAL_TASK1_ID;
  }

  public static PracticalTaskDto generatePracticalTaskDto() {
    return PracticalTaskDto.builder()
        .id(PRACTICAL_TASK1_ID)
        .taskText(TASK_TEXT)
        .imageDtos(generateImageDtos())
        .build();
  }

  public static List<PracticalTask> generateListPracticalTasks() {
    final PracticalTask practicalTask2 = generatePracticalTask();
    practicalTask2.setId(PRACTICAL_TASK2_ID);
    final PracticalTask practicalTask3 = generatePracticalTask();
    practicalTask3.setId(PRACTICAL_TASK3_ID);
    return List.of(generatePracticalTask(), practicalTask2, practicalTask3);
  }

  public static List<PracticalTaskDto> generateListPracticalTasksDto() {
    final PracticalTaskDto practicalTask2Dto = generatePracticalTaskDto();
    practicalTask2Dto.setId(PRACTICAL_TASK2_ID);
    final PracticalTaskDto practicalTask3Dto = generatePracticalTaskDto();
    practicalTask3Dto.setId(PRACTICAL_TASK3_ID);
    return List.of(generatePracticalTaskDto(), practicalTask2Dto, practicalTask3Dto);
  }
}
