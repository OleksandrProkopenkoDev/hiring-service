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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.hiringservice.exception.model.ErrorResponse;
import ua.com.hiringservice.model.dto.task.AssessPracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskAnswerDto;
import ua.com.hiringservice.model.dto.task.ProvidePracticalTaskAnswerDto;

/**
 * OpenAPI specification for {@link PracticalTaskAnswerDto} operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Tag(
    name = "Answers For Practical Tasks",
    description = "API for operations with practical tasks Answers")
public interface PracticalTaskAnswerOpenApi {

  @Operation(
      summary = "Finds List of all existing Answers on practical tasks. Pagination available",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Answers were found successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PracticalTaskAnswerDto.class))),
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
                                    "title"
                                  ]
                                }""")
  @GetMapping
  ResponseEntity<Page<PracticalTaskAnswerDto>> findAll(@PageableDefault Pageable pageable);

  @Operation(
      summary = "Finds Answer for practical Task by Id.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Answers were found successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PracticalTaskAnswerDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  @GetMapping("/{practicalTaskAnswerId}")
  ResponseEntity<PracticalTaskAnswerDto> findById(@PathVariable UUID practicalTaskAnswerId);

  @Operation(
      summary = "Provide Answer for Practical Task",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Answer was provided",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProvidePracticalTaskAnswerDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PutMapping("/{practicalTaskAnswerId}")
  ResponseEntity<ProvidePracticalTaskAnswerDto> provideAnswer(
      @PathVariable("practicalTaskAnswerId") UUID practicalTaskAnswerId,
      @Valid @RequestBody ProvidePracticalTaskAnswerDto answerDto);

  @Operation(
      summary = "Manual Assess the answer on practical task",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Answer was Assessed",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AssessPracticalTaskDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  @PatchMapping("{practicalTaskPassingId}/assess")
  ResponseEntity<AssessPracticalTaskDto> assessAnswerManually(
      @PathVariable UUID practicalTaskPassingId,
      @Valid @RequestBody AssessPracticalTaskDto assessDto);
}
