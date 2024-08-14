package ua.tc.marketplace.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.jwtAuth.AuthRequest;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.util.mapper.UserMapper;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public UserDto authentificate(AuthRequest requestBody) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestBody.email(),
                        requestBody.password())
        );//if auth failed, redirecting to login. Code below is not run
        SecurityContextHolder.getContext().setAuthentication(auth);
        User authUser = (User)auth.getPrincipal();

        return  userMapper.toDto(authUser);
    }

}