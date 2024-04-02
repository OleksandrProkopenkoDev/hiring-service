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
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.dto.task.QuizPassingResponseDto;
import ua.com.hiringservice.service.task.QuizPassingService;

/**
 * Controller for managing task passing operations for taskType = QUIZ.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/api/v1/task-passing/quiz")
@AllArgsConstructor
public class QuizPassingController {

  private final QuizPassingService quizPassingService;

  @GetMapping("/start")
  public ResponseEntity<Void> startTaskPassing(@RequestParam final UUID taskId) {
    quizPassingService.startQuizPassing(taskId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/get-question")
  public ResponseEntity<AnswerDto> getAnswerByQuestion(
      @RequestParam final UUID taskId, @RequestParam final Integer indexInTask) {

    return ResponseEntity.ok(quizPassingService.getAnswerByQuestion(taskId, indexInTask));
  }

  @GetMapping("/finish")
  public ResponseEntity<Void> finishTaskPassing(@RequestParam final UUID taskId) {

    quizPassingService.finishQuizPassing(taskId);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/assessed")
  public ResponseEntity<Page<QuizPassingResponseDto>> getAssessedDoneTaskPassings(
      @PageableDefault Pageable pageable) {

    return ResponseEntity.ok(quizPassingService.getAssessedDoneTaskPassings(pageable));
  }

  @GetMapping("/assessed/{taskId}")
  public ResponseEntity<Page<QuizPassingResponseDto>> getAssessedDoneTaskPassingsByTaskId(
      @PageableDefault Pageable pageable,
      @PathVariable("taskId") UUID taskId,
      @RequestParam(name = "userId", required = false) UUID userId) {
    return ResponseEntity.ok(
        quizPassingService.getAssessedDoneQuizPassingByTaskId(userId, taskId, pageable));
  }
}
