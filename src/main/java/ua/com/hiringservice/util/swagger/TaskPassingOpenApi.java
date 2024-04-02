package ua.com.hiringservice.util.swagger;

import static ua.com.hiringservice.util.swagger.SwaggerExamples.ANSWER_FOR_USER;
import static ua.com.hiringservice.util.swagger.SwaggerExamples.QUIZ_FOR_QUIZ_PASSING;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.hiringservice.exception.model.ErrorResponse;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskWithBytesDto;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskPassingDto;

/** OpenAPI specification for {@link TaskPassingDto} operations. */
@Tag(
    name = "Task Passing",
    description =
        "API for operations with TaskPassing. Task passing is just a History records "
            + "of users attempts to pass the Task (Quiz or Practical Task)")
public interface TaskPassingOpenApi {

  @Operation(
      summary = "Get Task for welcome page in Task passing",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Got Task without Task questions successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TaskDto.class),
                    examples = @ExampleObject(value = QUIZ_FOR_QUIZ_PASSING))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<TaskDto> getTaskForPassing(@PathVariable UUID taskId);

  @Operation(summary = "Start Task passing", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Task passing started successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<Void> startTaskPassing(@RequestParam final UUID taskId);

  @Operation(
      summary = "Get answer in Task passing",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Got answer for user successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AnswerDto.class),
                    examples = @ExampleObject(value = ANSWER_FOR_USER))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<AnswerDto> getAnswerByQuestion(
      @RequestParam final UUID taskId, @RequestParam final Integer sequence);

  @Operation(
      summary = "Get practical task answer with text fields as images",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Got answer for user successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PracticalTaskWithBytesDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<PracticalTaskWithBytesDto> getPracticalTaskImagedAnswer(
      @RequestParam final UUID taskId, @RequestParam Integer width);

  @Operation(summary = "Finish Task passing", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Task passing finished successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<Void> finishTaskPassing(@RequestParam final UUID taskId);

  @Operation(
      summary = "Get list of passed tasks",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Got list successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<Page<TaskPassingDto>> getAssessedDoneTaskPassings(
      @PageableDefault Pageable pageable);

  @Operation(
      summary = "Get passed task by taskId",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Got passed task successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<Page<TaskPassingDto>> getAssessedDoneTaskPassing(
      @PageableDefault Pageable pageable,
      @PathVariable("taskId") UUID taskId,
      @RequestParam(name = "userId", required = false) UUID userId);
}
