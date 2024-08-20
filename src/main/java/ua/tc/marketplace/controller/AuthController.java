package ua.tc.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.jwtAuth.JwtUtil;
import ua.tc.marketplace.model.auth.AuthError;
import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.UserRepository;
import ua.tc.marketplace.service.AuthService;
import ua.tc.marketplace.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody AuthRequest authRequest) {
//        System.out.println("auth request with body: "+requestBody);
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//        AuthResponse loginResponse = new AuthResponse(jwtToken).setToken().setExpiresIn(jwtService.getExpirationTime());
//        return ResponseEntity.ok(loginResponse);
//        return authService.authentificate(requestBody);



        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
            String token = jwtUtil.createToken(user);

            AuthResponse authResponse = new AuthResponse(email,token,"",0L);

            return ResponseEntity.ok(authResponse);

        }catch (BadCredentialsException e){
            AuthError authErrorResponse = new AuthError(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authErrorResponse);
        }catch (Exception e){
            AuthError authErrorResponse = new AuthError(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authErrorResponse);
        }

    }
}
