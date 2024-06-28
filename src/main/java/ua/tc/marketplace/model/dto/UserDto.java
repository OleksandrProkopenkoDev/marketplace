package ua.tc.marketplace.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.ContactInfo;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.model.enums.UserRole;

@Data
public class UserDto {
  private Long id;

  @Schema(example = "taras@shevchenko.ua")
  @NotBlank
  private String email;

  @Schema(example = "strong_secure_password_with_bigAndSmallLetters_and_digits_and_symbols")
  @NotBlank
  private String password;

  @Schema(example = "INDIVIDUAL")
  @NotBlank
  private String userRole;

  @Schema(example = "Taras")
  @NotBlank
  private String firstName;

  @Schema(example = "Shevchenko")
  private String lastName; // optional

  private Photo profilePicture;

  private ContactInfo contactInfo;

  private List<Ad> favorites;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
