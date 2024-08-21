package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.jwtAuth.JwtUtil;
import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.UserRepository;
import ua.tc.marketplace.service.AuthService;
import ua.tc.marketplace.util.mapper.UserMapper;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse authentificate(AuthRequest authRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
        String token = jwtUtil.createToken(user);

        AuthResponse authResponse = new AuthResponse(email,token,"",0L);

        return authResponse;
    }

}