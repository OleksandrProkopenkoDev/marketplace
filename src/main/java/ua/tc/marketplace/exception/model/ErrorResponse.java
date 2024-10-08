package ua.tc.marketplace.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.MediaType;

/**
 * Represents an error response returned by the API. Contains information about the error message,
 * timestamp, path, and status number.
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

  public void appendToResponse(HttpServletResponse response, ObjectMapper mapper) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    String errorResponseJson = mapper.writeValueAsString(this);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(errorResponseJson);
  }

}
