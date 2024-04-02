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
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.service.task.PracticalTaskService;
import ua.com.hiringservice.util.swagger.PracticalTaskOpenApi;

/**
 * Controller for handling practical task-related endpoints. Performs CRUD operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/api/v1/practical-task")
@AllArgsConstructor
public class PracticalTaskController implements PracticalTaskOpenApi {
  private final PracticalTaskService practicalTaskService;

  @Override
  @GetMapping
  public ResponseEntity<Page<PracticalTaskDto>> getAllPracticalTasks(
      @PageableDefault Pageable pageable) {
    return ResponseEntity.ok(practicalTaskService.findAll(pageable));
  }

  @Override
  @GetMapping("/{practicalTaskId}")
  public ResponseEntity<PracticalTaskDto> getPracticalTaskById(@PathVariable UUID practicalTaskId) {
    return ResponseEntity.ok(practicalTaskService.findById(practicalTaskId));
  }

  @Override
  @PostMapping
  public ResponseEntity<PracticalTaskDto> savePracticalTask(
      @Valid @RequestBody PracticalTaskDto practicalTaskDto) {
    final PracticalTaskDto saved = practicalTaskService.save(practicalTaskDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @Override
  @PutMapping("/{practicalTaskId}")
  public ResponseEntity<PracticalTaskDto> updatePracticalTask(
      @PathVariable UUID practicalTaskId, @Valid @RequestBody PracticalTaskDto practicalTaskDto) {
    final PracticalTaskDto updated = practicalTaskService.update(practicalTaskId, practicalTaskDto);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
  }

  @Override
  @DeleteMapping("/{practicalTaskId}")
  public ResponseEntity<Void> deletePracticalTask(@PathVariable UUID practicalTaskId) {
    practicalTaskService.deleteById(practicalTaskId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
