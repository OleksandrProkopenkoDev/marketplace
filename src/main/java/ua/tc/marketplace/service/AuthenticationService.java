package ua.tc.marketplace.service;

import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;
import ua.tc.marketplace.model.dto.user.CreateUserDto;

public interface AuthenticationService {

  AuthResponse authenticate(AuthRequest authRequest);

  AuthResponse registerUser(CreateUserDto userDto);
}
