package ua.com.hiringservice.controller.task;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.hiringservice.model.dto.task.AssessPracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskAnswerDto;
import ua.com.hiringservice.model.dto.task.ProvidePracticalTaskAnswerDto;
import ua.com.hiringservice.service.task.PracticalTaskAnswerService;
import ua.com.hiringservice.util.swagger.PracticalTaskAnswerOpenApi;

/**
 * Controller for handling practical task answers.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/api/v1/answers/practical-task")
@AllArgsConstructor
public class PracticalTaskAnswerController implements PracticalTaskAnswerOpenApi {
  private final PracticalTaskAnswerService practicalTaskAnswerService;

  @Override
  @GetMapping
  public ResponseEntity<Page<PracticalTaskAnswerDto>> findAll(
      @PageableDefault final Pageable pageable) {
    final Page<PracticalTaskAnswerDto> answers = practicalTaskAnswerService.findAll(pageable);
    return ResponseEntity.ok(answers);
  }

  @Override
  @GetMapping("/{practicalTaskAnswerId}")
  public ResponseEntity<PracticalTaskAnswerDto> findById(@PathVariable UUID practicalTaskAnswerId) {
    return ResponseEntity.ok(practicalTaskAnswerService.findById(practicalTaskAnswerId));
  }

  @Override
  @PutMapping("/{practicalTaskAnswerId}")
  public ResponseEntity<ProvidePracticalTaskAnswerDto> provideAnswer(
      @PathVariable("practicalTaskAnswerId") UUID practicalTaskAnswerId,
      @Valid @RequestBody ProvidePracticalTaskAnswerDto answerDto) {
    final ProvidePracticalTaskAnswerDto providedAnswer =
        practicalTaskAnswerService.provideAnswer(practicalTaskAnswerId, answerDto);
    return ResponseEntity.status(HttpStatus.OK).body(providedAnswer);
  }

  @Override
  @PatchMapping("{practicalTaskPassingId}/assess")
  public ResponseEntity<AssessPracticalTaskDto> assessAnswerManually(
      @PathVariable final UUID practicalTaskPassingId,
      @Valid @RequestBody AssessPracticalTaskDto assessDto) {
    final AssessPracticalTaskDto response =
        practicalTaskAnswerService.assessAnswerManually(practicalTaskPassingId, assessDto);
    return ResponseEntity.ok(response);
  }
}
