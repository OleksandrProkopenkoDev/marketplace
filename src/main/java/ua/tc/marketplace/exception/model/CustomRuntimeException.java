package ua.tc.marketplace.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base class for custom RuntimeException in the application. All custom RuntimeException classes
 * should extend this class.
 */
@Getter
public abstract class CustomRuntimeException extends RuntimeException {

  /** The exception message. */
  protected String exceptionMessage;

  /** The HTTP status associated with the exception. */
  protected HttpStatus httpStatus;

  /**
   * Constructs a new custom exception with the specified cause and HTTP status.
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
   * @param httpStatus the HTTP status associated with the exception
   */
  public CustomRuntimeException(Throwable cause, HttpStatus httpStatus) {
    super(cause);
    this.httpStatus = httpStatus;
  }

  /**
   * Constructs a new custom exception with the specified message and HTTP status.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *     #getMessage()} method)
   * @param httpStatus the HTTP status associated with the exception
   */
  public CustomRuntimeException(String message, HttpStatus httpStatus) {
    super(message);
    this.exceptionMessage = message;
    this.httpStatus = httpStatus;
  }

  /**
   * Constructs a new custom exception with the specified message, cause, and HTTP status.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link
   *     #getMessage()} method)
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
   * @param httpStatus the HTTP status associated with the exception
   */
  public CustomRuntimeException(String message, Throwable cause, HttpStatus httpStatus) {
    super(message, cause);
    this.exceptionMessage = message;
    this.httpStatus = httpStatus;
  }
}

