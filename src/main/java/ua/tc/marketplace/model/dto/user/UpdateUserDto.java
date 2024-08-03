package ua.tc.marketplace.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.ContactInfo;
import ua.tc.marketplace.model.entity.Photo;

public record UpdateUserDto(
    Long id,
    @Schema(example = "taras@shevchenko.ua")
    @NotBlank
    String email,

    @Schema(example = "strong_secure_password_with_bigAndSmallLetters_and_digits_and_symbols")
    @NotBlank
    String password,

    @Schema(example = "INDIVIDUAL")
    @NotBlank
    String userRole,

    @Schema(example = "Taras")
    @NotBlank
    String firstName,

    @Schema(example = "Shevchenko")
    String lastName, // optional

    ContactInfo contactInfo,

    List<Ad> favorites
) {

}
