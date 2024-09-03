package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.auth.BadCredentialsAuthenticationException;
import ua.tc.marketplace.exception.auth.GeneralAuthenticationException;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.jwtAuth.JwtConfig;
import ua.tc.marketplace.jwtAuth.JwtUtil;
import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.service.AuthenticationService;
import ua.tc.marketplace.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final JwtConfig jwtConfig;
  private final UserService userService;

  /**
   * Authentificats a user/
   *
   * @param authRequest The email of the user to retrieve.
   * @return The UserDto representing the found user.
   * @throws UserNotFoundException If the user is not found.
   */
  @Override
  public AuthResponse authenticate(AuthRequest authRequest) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
      String email = authentication.getName();
      User user = userService.findUserByEmail(email);
      String token = jwtUtil.createToken(user);
      return new AuthResponse(email, token, "", jwtConfig.getTokenExpirationAfterSeconds());
    } catch (BadCredentialsException e) {
      throw new BadCredentialsAuthenticationException();
    } catch (Exception e) {
      throw new GeneralAuthenticationException(e.getMessage());
    }
  }

  @Override
  public AuthResponse registerUser(CreateUserDto userDto) {
    UserDto user = userService.createUser(userDto);
    log.info(user.toString());
    return authenticate(new AuthRequest(user.email(), user.password()));
  }
}
