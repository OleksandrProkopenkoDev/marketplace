package ua.tc.marketplace.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Represents an error response returned by the API. Contains information about the error message,
 * timestamp, filename, and status number.
 */
@Data
public class ErrorResponse {

  @Schema(
      description = "Message",
      example = "Full authentication is required to access this resource")
  private String message;

  @Schema(description = "Time when received response", example = "2023-01-01 00:00:00")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;

  @Schema(description = "Path", example = "/api/v1/user")
  private String path;

  @Schema(description = "Status number", example = "401")
  private Integer status;


  public ErrorResponse(Integer status, String message, String path) {
    timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.path = path;
  }

}
