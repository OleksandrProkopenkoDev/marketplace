package ua.tc.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.jwtAuth.AuthRequest;
import ua.tc.marketplace.jwtAuth.AuthResponse;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest requestBody) {
        System.out.println("auth request with body: "+requestBody);




        String jwtToken = jwtService.generateToken(authenticatedUser);

        AuthResponse loginResponse = new AuthResponse(jwtToken).setToken().setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);


        return authService.authentificate(requestBody);

    }
}
