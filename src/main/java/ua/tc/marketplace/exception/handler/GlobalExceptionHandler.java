package ua.tc.marketplace.exception.handler;

import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.tc.marketplace.exception.model.CustomIOException;
import ua.tc.marketplace.exception.model.CustomIllegalStateException;
import ua.tc.marketplace.exception.model.CustomRuntimeException;
import ua.tc.marketplace.exception.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
      AuthenticationException.class,
      AccessDeniedException.class,
  })
  protected ResponseEntity<ErrorResponse> handleSecurityExceptions(
      Exception ex, ServletWebRequest request) {
    HttpStatus status;
    if (ex instanceof AuthenticationException) {
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.FORBIDDEN;
    }
    final ErrorResponse errorResponseBody = buildErrorResponse(ex, request, status);
    writeErrorLog(errorResponseBody);
    return ResponseEntity.status(status).body(errorResponseBody);
  }

  @ExceptionHandler({
    CustomRuntimeException.class,
    CustomIllegalStateException.class,
    CustomIOException.class
  })
  protected ResponseEntity<ErrorResponse> handleCustomExceptions(
      CustomRuntimeException ex, ServletWebRequest request) {
    final HttpStatus status = ex.getHttpStatus();
    final ErrorResponse errorResponseBody = buildErrorResponse(ex, request, status);
    writeErrorLog(errorResponseBody);
    return ResponseEntity.status(status).body(errorResponseBody);
  }

  private ErrorResponse buildErrorResponse(
      final Exception ex, final ServletWebRequest request, final HttpStatus status) {
    final String requestURI = getRequestURI(request);
    return new ErrorResponse(status.value(), ex.getMessage(), requestURI);
  }

  private ErrorResponse buildErrorResponse(
      ProblemDetail detail, HttpStatus status, WebRequest request) {
    final String errorMessage = (detail != null) ? detail.getDetail() : "Unknown error occurred";
    final String requestURI = getRequestURI(request);
    return new ErrorResponse(status.value(), errorMessage, requestURI);
  }

  private String getRequestURI(WebRequest request) {
    if (request instanceof ServletWebRequest) {
      return ((ServletWebRequest) request).getRequest().getRequestURI();
    } else {
      // If WebRequest is not a ServletWebRequest, return a default URI
      return "Unknown URI";
    }
  }

  private void writeErrorLog(ErrorResponse response) {
    logger.error(
        new StringWriter()
            .append("ERROR : ")
            .append(response.getMessage())
            .append(", PATH : ")
            .append(response.getPath())
            .toString());
  }
}
