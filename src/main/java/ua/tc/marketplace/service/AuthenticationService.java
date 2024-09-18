package ua.tc.marketplace.service;

import java.util.Optional;
import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.entity.User;

public interface AuthenticationService {

  AuthResponse authenticate(AuthRequest authRequest);

  AuthResponse registerUser(CreateUserDto userDto);

  Optional<User> getAuthenticatedUser();
}
