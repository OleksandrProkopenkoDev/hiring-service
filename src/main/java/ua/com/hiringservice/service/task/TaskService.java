package ua.com.hiringservice.service.task;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.entity.task.Task;

/**
 * Service interface for managing tasks and associated questions. Defines methods for creating,
 * retrieving, updating, and deleting tasks, as well as operations related to task questions.
 *
 * @author Oleksandr Prokopenko
 * @version 1.2
 * @since 2024-01-04
 */
public interface TaskService {

  TaskDto createTask(TaskDto taskDto);

  TaskDto findById(UUID id);

  Page<TaskDto> findAll(Pageable pageable);

  TaskDto updateTask(UUID taskId, TaskDto taskDto);

  void deleteTaskById(UUID id);

  TaskDto publish(UUID taskId);

  TaskDto unPublish(UUID taskId);

  TaskDto getTaskForPassing(UUID taskId);

  Task findTaskById(UUID taskId);
}
