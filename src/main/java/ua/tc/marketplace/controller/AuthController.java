package ua.tc.marketplace.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tc.marketplace.model.dto.auth.AuthRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
    try {
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
      Authentication authentication = authenticationManager.authenticate(authToken);

      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Start a new session (if you are using session-based authentication)
      request.getSession();

      return ResponseEntity.ok("Successfully authenticated");

    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
  }
}
