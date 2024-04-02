package ua.com.hiringservice.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/** Replace this stub by correct Javadoc. */
@Data
public class ErrorResponse {
  @Schema(description = "Status number", example = "401")
  private Integer status;

  @Schema(description = "Time when recieved response", example = "01-01-2023 00:00:00")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  @Schema(
      description = "Message",
      example = "Full authentication is required to access this resource")
  private String message;

  @Schema(description = "Path", example = "/api/students")
  private String path;

  public ErrorResponse(Integer status, String message, String path) {
    timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.path = path;
  }
}
