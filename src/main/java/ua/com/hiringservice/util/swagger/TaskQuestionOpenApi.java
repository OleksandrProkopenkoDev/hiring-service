package ua.com.hiringservice.util.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.hiringservice.exception.model.ErrorResponse;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;

/**
 * This interface represents the OpenAPI specification for task Questions in the taskService. It
 * includes operations to add and delete taskQuestions from a task.
 */
@Tag(name = "TaskQuestions", description = "API for operations with TaskQuestions")
public interface TaskQuestionOpenApi {

  @Operation(
      summary = "Add new TaskQuestion to Task",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "TaskQuestion was added successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TaskDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<TaskDto> createQuestionByTaskId(
      @PathVariable UUID taskId, @RequestBody TaskQuestionDto taskQuestionDto);

  @Operation(
      summary = "Delete taskQuestion from task",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "taskQuestion was deleted successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "task not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<TaskDto> deleteTaskQuestionFromTask(
      @PathVariable UUID taskId, @PathVariable UUID taskQuestionId);

  @Operation(
      summary = "Update taskQuestion by id",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "taskQuestion was updated successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TaskDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "taskQuestion not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<TaskDto> updateTaskQuestion(
      @PathVariable UUID taskId,
      @PathVariable UUID taskQuestionId,
      @Valid @RequestBody TaskQuestionDto taskQuestionDto);
}
