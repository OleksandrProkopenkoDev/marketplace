package ua.tc.marketplace.model.dto.photo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.http.HttpHeaders;

/**
 * DTO record representing a response containing multiple files.
 *
 * This record encapsulates a list of file contents as byte arrays and HTTP headers.
 * It includes validation constraints to ensure that the list of contents is not null
 * and that the headers are not null.
 */
public record FilesResponse(
    @NotNull(message = "File contents list cannot be null.")
    @Size(min = 1, message = "There must be at least one file content.")
    List<byte[]> contents,

    @NotNull(message = "HTTP headers cannot be null.")
    HttpHeaders headers
) {}