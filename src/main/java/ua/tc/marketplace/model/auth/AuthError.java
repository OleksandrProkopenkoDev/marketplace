package ua.tc.marketplace.model.auth;


import org.springframework.http.HttpStatus;

public record AuthError(HttpStatus httpStatus, String message) {

}