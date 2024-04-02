package ua.com.hiringservice.controller.task;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.hiringservice.model.dto.task.PracticalTaskPassingResponseDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskWithBytesDto;
import ua.com.hiringservice.service.task.PracticalTaskPassingService;

/**
 * Controller for managing task passing operations for taskType = PRACTICAL_TASK.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/api/v1/task-passing/practical-task")
@AllArgsConstructor
public class PracticalTaskPassingController {

  private final PracticalTaskPassingService practicalTaskPassingService;

  @GetMapping("/start")
  public ResponseEntity<Void> startTaskPassing(@RequestParam final UUID taskId) {
    practicalTaskPassingService.startPracticalTaskPassing(taskId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/get")
  public ResponseEntity<PracticalTaskWithBytesDto> getPracticalTaskImagedAnswer(
      @RequestParam final UUID taskId, @RequestParam Integer width) {
    return ResponseEntity.ok(practicalTaskPassingService.getPracticalTaskImaged(taskId, width));
  }

  @GetMapping("/assessed")
  public ResponseEntity<Page<PracticalTaskPassingResponseDto>> getAssessedDoneTaskPassings(
      @PageableDefault Pageable pageable) {
    return ResponseEntity.ok(
        practicalTaskPassingService.getAssessedDonePracticalTaskPassings(pageable));
  }

  @GetMapping("/assessed/{taskId}")
  public ResponseEntity<Page<PracticalTaskPassingResponseDto>> getAssessedDoneTaskPassingByTaskId(
      @PageableDefault Pageable pageable,
      @PathVariable("taskId") UUID taskId,
      @RequestParam(name = "userId", required = false) UUID userId) {
    return ResponseEntity.ok(
        practicalTaskPassingService.getAssessedDonePracticalTaskPassingsByTaskId(
            userId, taskId, pageable));
  }
}
