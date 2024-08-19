package ua.tc.marketplace.jwtAuth;


public record AuthResponse(

     String accessToken,
     String refreshToken, // Optional, if using refresh tokens
    Long expiresIn){ // Optional, seconds until expiration

}