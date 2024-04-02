package ua.com.hiringservice.util.swagger;

import static ua.com.hiringservice.util.swagger.SwaggerExamples.ERROR_401;
import static ua.com.hiringservice.util.swagger.SwaggerExamples.ERROR_404;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ua.com.hiringservice.exception.SpecializationNameAlreadyExistException;
import ua.com.hiringservice.exception.model.ErrorResponse;
import ua.com.hiringservice.model.dto.vacancy.SpecializationCreateDto;
import ua.com.hiringservice.model.dto.vacancy.SpecializationDto;
import ua.com.hiringservice.model.dto.vacancy.SpecializationIconDto;

/** Replace this stub by correct Javadoc. */
@SuppressWarnings("PMD")
@Tag(name = "Specialization", description = "API for operations with specializations")
public interface SpecializationOpenApi {

  @Operation(
      summary = "Create specialization.",
      description = "Create a new specialization. Required role: ROLE-RECRUITER",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_201,
            description = "Specialization was created successfully",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SpecializationCreateDto.class))),
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_401,
            description = SwaggerExamples.UNAUTHORIZED,
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_401))),
        @ApiResponse(
            responseCode = "409",
            description = "Specialization name already exist.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(implementation = SpecializationNameAlreadyExistException.class),
                    examples =
                        @ExampleObject(value = "Specialization with this name already exist"))),
      })
  ResponseEntity<SpecializationDto> save(
      @Valid @RequestPart("specialization") @Parameter(description = "Specialization name.")
          SpecializationCreateDto specializationCreateDto,
      @RequestPart("iconFile")
          @Parameter(description = "Icon file. Max size: 16MB. Formats: jpg, jpeg, png, pdf")
          MultipartFile iconFile)
      throws IOException;

  @Operation(
      summary = "Get list of all specializations",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Specializations retrieved successfully.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array =
                        @ArraySchema(schema = @Schema(implementation = SpecializationDto.class)))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized access attempt.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_401))),
      })
  ResponseEntity<List<SpecializationDto>> getAll();

  @Operation(
      summary = "Get specialization by id",
      description = "Retrieves a specific specialization by its ID. Required role: ROLE-RECRUITER",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_200,
            description = "Specialization was found successfully.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SpecializationDto.class))),
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_401,
            description = SwaggerExamples.UNAUTHORIZED,
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_401))),
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_404,
            description = "Specialization was found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_404)))
      })
  ResponseEntity<SpecializationDto> getById(@PathVariable Long specializationId);

  @Operation(
      summary = "Delete specialization by id",
      description = "Delete a specific specialization by its ID.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_204,
            description = "Specialization was deleted successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_401,
            description = SwaggerExamples.UNAUTHORIZED,
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_401))),
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_404,
            description = "Specialization was found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_404)))
      })
  ResponseEntity<Void> deleteById(@PathVariable Long specializationId);

  @Operation(
      summary = "Get icon by specialization id",
      description = "Retrieves a specific icon by specialization ID.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_200,
            description = "Icon was found successfully.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SpecializationIconDto.class))),
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_401,
            description = SwaggerExamples.UNAUTHORIZED,
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_401))),
        @ApiResponse(
            responseCode = SwaggerExamples.RESPONSE_CODE_404,
            description = "Icon was found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_404)))
      })
  ResponseEntity<SpecializationIconDto> getIconById(@PathVariable Long specializationId);

  @Operation(
      summary = "Add or update specialization's icon file",
      description = "Updating existing icon.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Icon of specialization updated successfully.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SpecializationDto.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized access attempt.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_401))),
        @ApiResponse(
            responseCode = "404",
            description = "Verification details not found.",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ERROR_404)))
      })
  ResponseEntity<SpecializationDto> updateIconById(
      @PathVariable Long specializationId,
      @RequestPart("iconFile") @Parameter(description = "Icon file. Formats: jpg, jpeg, png, pdf")
          MultipartFile iconFile)
      throws IOException;
}
