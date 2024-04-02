package ua.com.hiringservice.controller.task;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.service.task.PracticalTaskService;
import ua.com.hiringservice.service.task.TaskQuestionService;
import ua.com.hiringservice.util.swagger.TaskQuestionOpenApi;

/**
 * Controller class for handling task questions related operations.
 *
 * <p>This class defines REST endpoints for managing task questions within tasks. It includes
 * methods for creating and deleting task questions associated with a specific task.
 *
 * @version 1.0.
 * @since 2024-01-21
 */
@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskQuestionController implements TaskQuestionOpenApi {

  private final TaskQuestionService taskQuestionService;
  private final PracticalTaskService practicalTaskService;

  @Override
  @PostMapping("/{taskId}/task-question")
  public ResponseEntity<TaskDto> createQuestionByTaskId(
      @PathVariable UUID taskId, @Valid @RequestBody TaskQuestionDto taskQuestionDto) {
    final TaskDto result = taskQuestionService.addTaskQuestionToTask(taskId, taskQuestionDto);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @Override
  @DeleteMapping("/{taskId}/task-question/{taskQuestionId}")
  public ResponseEntity<TaskDto> deleteTaskQuestionFromTask(
      @PathVariable UUID taskId, @PathVariable UUID taskQuestionId) {
    return ResponseEntity.ok(
        taskQuestionService.deleteTaskQuestionFromTask(taskId, taskQuestionId));
  }

  @Override
  @PutMapping("/{taskId}/task-question/{taskQuestionId}")
  public ResponseEntity<TaskDto> updateTaskQuestion(
      @PathVariable UUID taskId,
      @PathVariable UUID taskQuestionId,
      @Valid @RequestBody TaskQuestionDto taskQuestionDto) {
    final TaskDto result =
        taskQuestionService.updateTaskQuestion(taskId, taskQuestionId, taskQuestionDto);

    return ResponseEntity.ok(result);
  }

  @PutMapping("/{taskId}/add-practical-task")
  public ResponseEntity<TaskDto> addPracticalTaskToTask(
      @PathVariable UUID taskId, @RequestParam UUID practicalTaskId) {
    final TaskDto result = practicalTaskService.addPracticalTaskToTask(taskId, practicalTaskId);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping("/{taskId}/add-practical-task")
  public ResponseEntity<TaskDto> addPracticalTaskToTask(@PathVariable UUID taskId) {
    final TaskDto result = practicalTaskService.removePracticalTaskFromTask(taskId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
