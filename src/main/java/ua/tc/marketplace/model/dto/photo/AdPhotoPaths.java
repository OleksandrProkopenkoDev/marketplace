package ua.tc.marketplace.model.dto.photo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO record representing advertisement photo paths.
 *
 * <p>This record encapsulates the ID of the advertisement and an array of strings representing the
 * file paths of the photos. It includes validation constraints to ensure that the advertisement ID
 * is provided and that the array of paths is not empty, does not exceed a certain number of paths,
 * and that each path matches a specified pattern.
 */
public record AdPhotoPaths(
    @NotNull(message = "Advertisement ID cannot be null.") Long adId,
    @NotNull(message = "Paths cannot be null.")
        @Size(min = 1, max = 10, message = "The number of paths must be between 1 and 10.")
        @Pattern(regexp = "^[a-zA-Z0-9_/.-]+$", message = "Each path must be a valid file path.")
        String[] paths) {}
