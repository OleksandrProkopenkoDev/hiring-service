package ua.com.hiringservice.util.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.hiringservice.exception.model.ErrorResponse;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;

/**
 * OpenAPI specification for {@link PracticalTask} operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Tag(name = "Practical Tasks", description = "API for operations with Practical")
public interface PracticalTaskOpenApi {

  @Operation(
      summary =
          "Finds all PracticalTasks. Sorting by PracticalTask fields available. Pagination"
              + " available",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Practical Tasks were found successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PracticalTaskDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  @Parameter(
      name = "pageable",
      description = "Pageable information(page, size, sort)",
      example =
          """
      {
        "page": 0,
        "size": 10,
        "sort": [
          "taskText"
        ]
      }""")
  @GetMapping
  ResponseEntity<Page<PracticalTaskDto>> getAllPracticalTasks(@PageableDefault Pageable pageable);

  @Operation(
      summary = "Find Practical Task by id",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Practical Task was found successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PracticalTaskDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/{practicalTaskId}")
  ResponseEntity<PracticalTaskDto> getPracticalTaskById(@PathVariable UUID practicalTaskId);

  @Operation(
      summary = "Create Practical Task",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Practical Task was created successfully, diagrams were stored in db.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PracticalTaskDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping
  ResponseEntity<PracticalTaskDto> savePracticalTask(
      @Valid @RequestBody PracticalTaskDto practicalTaskDto);

  @Operation(
      summary = "Update PracticalTask by id",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "PracticalTask was updated successfully. Old diagrams were deleted, new diagrams"
                    + " were stored.",
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
            description = "PracticalTask not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PutMapping("/{practicalTaskId}")
  ResponseEntity<PracticalTaskDto> updatePracticalTask(
      @PathVariable UUID practicalTaskId, @Valid @RequestBody PracticalTaskDto practicalTaskDto);

  @Operation(
      summary = "Delete PracticalTask by id",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "PracticalTask and all its diagrams were deleted successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @DeleteMapping("/{practicalTaskId}")
  ResponseEntity<Void> deletePracticalTask(@PathVariable UUID practicalTaskId);
}
