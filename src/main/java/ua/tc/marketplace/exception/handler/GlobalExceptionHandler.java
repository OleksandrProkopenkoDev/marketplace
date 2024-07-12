package ua.tc.marketplace.exception.handler;

import java.io.StringWriter;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.tc.marketplace.exception.model.CustomIOException;
import ua.tc.marketplace.exception.model.CustomIllegalStateException;
import ua.tc.marketplace.exception.model.CustomRuntimeException;
import ua.tc.marketplace.exception.model.ErrorResponse;

/**
 * Global exception handler for handling various exceptions in the application.
 *
 * <p>This handler intercepts and processes exceptions thrown during request processing. It provides
 * customized error responses for specific exception types, such as security-related exceptions
 * (AuthenticationException, AccessDeniedException), custom runtime exceptions, and validation
 * errors (MethodArgumentNotValidException). The handler logs detailed error messages and generates
 * appropriate HTTP responses with error details.
 */
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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    // Collect default messages from the validation errors
    Map<String, String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

    // Create a custom message from the collected errors
    String defaultMessage = String.join(", ", errors.values());

    final ErrorResponse errorResponseBody =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), defaultMessage, getRequestURI(request));
    writeErrorLog(errorResponseBody);

    return new ResponseEntity<>(errorResponseBody, headers, status);
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
