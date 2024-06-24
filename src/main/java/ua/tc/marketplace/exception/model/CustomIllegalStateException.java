package ua.tc.marketplace.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base class for custom IllegalStateException exceptions in the application. All custom
 * IllegalStateException classes should extend this class.
 */
@Getter
public abstract class CustomIllegalStateException extends IllegalStateException {

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
  public CustomIllegalStateException(Throwable cause, HttpStatus httpStatus) {
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
  public CustomIllegalStateException(String message, HttpStatus httpStatus) {
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
  public CustomIllegalStateException(String message, Throwable cause, HttpStatus httpStatus) {
    super(message, cause);
    this.exceptionMessage = message;
    this.httpStatus = httpStatus;
  }
}
