package ua.tc.marketplace.model.dto.photo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO record representing advertisement photos.
 *
 * This record encapsulates the ID of the advertisement and an array of
 * multipart files representing the photos. It includes validation constraints
 * to ensure that the advertisement ID is provided and that the array of
 * files is not empty and does not exceed a certain number of files.
 */
public record AdPhotosDto(
    @NotNull(message = "Advertisement ID cannot be null.") Long adId,
    @NotNull(message = "Files cannot be null.")
        @Size(min = 1, max = 10, message = "The number of files must be between 1 and 10.")
        MultipartFile[] files) {}
