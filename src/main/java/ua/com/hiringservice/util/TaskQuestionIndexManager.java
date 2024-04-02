package ua.com.hiringservice.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskQuestion;

/**
 * The TaskQuestionIndexManager class provides utility methods for managing indexes of task
 * questions within a task. It offers functionality to retrieve the next available index and
 * organize the indexes of existing task questions (delete the gaps if taskQuestion was deleted).
 */
public final class TaskQuestionIndexManager {

  private TaskQuestionIndexManager() {
    throw new UnsupportedOperationException(
        "Utility TaskQuestionIndexManager class cannot be instantiated");
  }

  public static Integer getNextIndex(Task task) {
    organizeIndexesIn(task);
    return task.getTaskQuestions().size() + 1;
  }

  public static void organizeIndexesIn(Task task) {
    final List<TaskQuestion> taskQuestions =
        task.getTaskQuestions().stream()
            .sorted(Comparator.comparingInt(TaskQuestion::getIndexInTask))
            .toList();

    // Create a map to store the original indexes of the task questions
    final Map<UUID, Integer> originalIndexes =
        taskQuestions.stream()
            .collect(Collectors.toMap(TaskQuestion::getId, q -> taskQuestions.indexOf(q) + 1));

    // Update the indexInTask values
    for (final TaskQuestion taskQuestion : taskQuestions) {
      taskQuestion.setIndexInTask(originalIndexes.get(taskQuestion.getId()));
    }
  }
}
