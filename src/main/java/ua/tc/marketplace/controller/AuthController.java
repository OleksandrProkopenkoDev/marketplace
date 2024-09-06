package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.service.AuthenticationService;
import ua.tc.marketplace.util.openapi.AuthOpenApi;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthController implements AuthOpenApi {

  private final AuthenticationService authenticationService;

  @Override
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) {
    log.info("Login request: {}", authRequest);
    return ResponseEntity.ok(authenticationService.authenticate(authRequest));
  }

  @Override
  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody CreateUserDto userDto) {
    log.info("Register user request: {}", userDto);
    return ResponseEntity.status(HttpStatus.OK).body(authenticationService.registerUser(userDto));
  }
}
