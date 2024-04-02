package ua.com.hiringservice.util.swagger;

import static ua.com.hiringservice.util.swagger.SwaggerExamples.PAGEABLE;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.hiringservice.exception.model.ErrorResponse;
import ua.com.hiringservice.model.dto.filter.QuestionFilterCriteria;
import ua.com.hiringservice.model.dto.task.QuestionDto;

@Tag(name = "Questions", description = "API for operations with questions")
public interface QuestionOpenApi {
  @Operation(summary = "Find question by id", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Question was found successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = QuestionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(
            responseCode = "404",
            description = "Question not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<QuestionDto> findById(@PathVariable UUID id);

  @Operation(
      summary = "Finds all questions matching the given filter criteria",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Questions were found successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = QuestionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  @Parameter(
      name = "pageable",
      description = "Pageable information(page, size, sort)",
      example = PAGEABLE)
  ResponseEntity<Page<QuestionDto>> findAll(
      @PageableDefault Pageable pageable, @Valid QuestionFilterCriteria filterCriteria);

  @Operation(summary = "Create question", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Question was created successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = QuestionDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<QuestionDto> create(@RequestBody @Valid QuestionDto question);

  @Operation(
      summary = "Update question by id",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Question was updated successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = QuestionDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Question not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<QuestionDto> update(
      @PathVariable UUID id, @RequestBody @Valid QuestionDto question);

  @Operation(
      summary = "Delete question by id",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Question was deleted successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Question not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<Void> deleteById(@PathVariable UUID id);
}
