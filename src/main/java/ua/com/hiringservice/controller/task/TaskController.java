package ua.com.hiringservice.controller.task;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.util.swagger.TaskOpenApi;

/**
 * RESTful controller for managing task-related operations through API endpoints. Handles requests
 * related to tasks and their associated questions.
 *
 * @author Oleksandr Prokopenko
 * @version 1.2
 * @since 2024-01-04
 */
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TaskController implements TaskOpenApi {

  private final TaskService taskService;

  /**
   * Creates a new task based on the provided {@link TaskDto}.
   *
   * @param taskDto DTO containing information to create the task.
   * @return ResponseEntity containing the DTO representation of the created task.
   */
  @Override
  @PostMapping("/tasks")
  public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
    final TaskDto result = taskService.createTask(taskDto);

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  /**
   * Retrieves a specific task by its unique identifier.
   *
   * @param taskId The unique identifier of the task.
   * @return ResponseEntity containing the DTO representation of the task.
   */
  @Override
  @GetMapping("/tasks/{taskId}")
  public ResponseEntity<TaskDto> findById(@PathVariable UUID taskId) {
    final TaskDto result = taskService.findById(taskId);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/task-passing/{taskId}")
  public ResponseEntity<TaskDto> getTaskForPassing(@PathVariable UUID taskId) {
    return ResponseEntity.ok(taskService.getTaskForPassing(taskId));
  }

  /**
   * Retrieves a paginated list of taskzes.
   *
   * @param pageable Pagination information.
   * @return ResponseEntity containing a paginated list of task DTOs.
   */
  @Override
  @GetMapping("/tasks")
  public ResponseEntity<Page<TaskDto>> findAll(@PageableDefault Pageable pageable) {
    final Page<TaskDto> result = taskService.findAll(pageable);

    return ResponseEntity.ok(result);
  }

  /**
   * Updates an existing task based on the provided {@link TaskDto}.
   *
   * @param taskId The unique identifier of the task to be updated.
   * @param taskDto DTO containing updated information for the task.
   * @return ResponseEntity containing the DTO representation of the updated task.
   */
  @Override
  @PutMapping("/tasks/{taskId}")
  public ResponseEntity<TaskDto> updateTask(
      @PathVariable UUID taskId, @Valid @RequestBody TaskDto taskDto) {
    final TaskDto result = taskService.updateTask(taskId, taskDto);

    return ResponseEntity.ok(result);
  }

  @Override
  @DeleteMapping("/tasks/{taskId}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID taskId) {
    taskService.deleteTaskById(taskId);
    return ResponseEntity.noContent().build();
  }

  @Override
  @PostMapping("/tasks/{taskId}/publish")
  public ResponseEntity<TaskDto> publishTask(@PathVariable UUID taskId) {

    return ResponseEntity.ok(taskService.publish(taskId));
  }

  @Override
  @PostMapping("/tasks/{taskId}/un-publish")
  public ResponseEntity<TaskDto> unPublishTask(@PathVariable UUID taskId) {

    return ResponseEntity.ok(taskService.unPublish(taskId));
  }
}
