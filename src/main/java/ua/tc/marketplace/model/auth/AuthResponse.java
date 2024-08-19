package ua.tc.marketplace.model.auth;


public record AuthResponse(
    String email,
     String accessToken,
     String refreshToken, // Optional, if using refresh tokens
    Long expiresIn){ // Optional, seconds until expiration

}