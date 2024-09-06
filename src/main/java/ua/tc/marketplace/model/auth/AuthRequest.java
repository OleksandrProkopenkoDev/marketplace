package ua.tc.marketplace.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents an authentication request containing the user's email and password. The email field
 * must be a valid email address and cannot be blank. The password field must be between 8 and 24
 * characters long and cannot be blank.
 */
public record AuthRequest(
    @NotBlank @Email String email, @NotBlank @Size(min = 8, max = 24) String password) {

  @Override
  public String toString() {
    String hiddenPassword = "*".repeat(password.length());
    return "AuthRequest{" +
        "email='" + email + '\'' +
        ", password='" + hiddenPassword + '\'' +
        '}';
  }
}
