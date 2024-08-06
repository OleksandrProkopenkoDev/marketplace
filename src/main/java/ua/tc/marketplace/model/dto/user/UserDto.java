package ua.tc.marketplace.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ua.tc.marketplace.model.entity.ContactInfo;
import ua.tc.marketplace.model.entity.Photo;

/**
 * Data Transfer Object (DTO) representing a user.
 * Used for transferring user data between layers of the application.
 * This DTO includes all user information.
 *
 * <p>Validation constraints are applied to ensure data integrity and consistency.
 */
public record UserDto(
    Long id,
    @Schema(example = "taras@shevchenko.ua") @NotBlank String email,
    @Schema(example = "strong_secure_password_with_bigAndSmallLetters_and_digits_and_symbols")
        @NotBlank
        String password,
    @Schema(example = "INDIVIDUAL") @NotBlank String userRole,
    @Schema(example = "Taras") @NotBlank String firstName,
    @Schema(example = "Shevchenko") String lastName, // optional
    Photo profilePicture,
    ContactInfo contactInfo,
    List<Long> favoriteAdIds,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

  public UserDto {
    // Default values for nullable fields
    if (favoriteAdIds == null) {
      favoriteAdIds = new ArrayList<>();
    }
  }
}
