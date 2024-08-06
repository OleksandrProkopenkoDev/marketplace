package ua.tc.marketplace.model.dto.photo;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;

/**
 * DTO record representing a file response.
 *
 * <p>This record encapsulates the content of a file as a byte array and HTTP headers. It includes
 * validation constraints to ensure that the content is not null and that the headers are not null.
 */
public record FileResponse(
    @NotNull(message = "File content cannot be null.") byte[] content,
    @NotNull(message = "HTTP headers cannot be null.") HttpHeaders headers) {}
