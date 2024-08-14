package ua.tc.marketplace.jwtAuth;

public record AuthRequest(
        String email,
        String password
) {

}
