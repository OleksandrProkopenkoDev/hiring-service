package ua.com.hiringservice.service.task;

import java.util.UUID;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.model.entity.task.Task;

/** Service interface for managing TaskQuestions within a Task. */
public interface TaskQuestionService {

  TaskDto addTaskQuestionToTask(UUID taskId, TaskQuestionDto taskQuestionDto);

  TaskDto deleteTaskQuestionFromTask(UUID taskId, UUID taskQuestionId);

  TaskDto updateTaskQuestion(UUID taskId, UUID taskQuestionId, TaskQuestionDto taskQuestionDto);

  void fetchQuestionsFromDbTo(Task task);
}
