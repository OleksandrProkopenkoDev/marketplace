package ua.tc.marketplace.model.auth;

public record AuthRequest(
        String email,
        String password
) {

}
