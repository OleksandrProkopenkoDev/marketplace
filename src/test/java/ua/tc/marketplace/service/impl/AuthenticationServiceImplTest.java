package ua.tc.marketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ua.tc.marketplace.exception.auth.BadCredentialsAuthenticationException;
import ua.tc.marketplace.exception.auth.GeneralAuthenticationException;
import ua.tc.marketplace.jwtAuth.JwtConfig;
import ua.tc.marketplace.jwtAuth.JwtUtil;
import ua.tc.marketplace.model.auth.AuthRequest;
import ua.tc.marketplace.model.auth.AuthResponse;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtUtil jwtUtil;
  @Mock private JwtConfig jwtConfig;
  @Mock private UserService userService;

  @InjectMocks private AuthenticationServiceImpl underTest;

  @Test
  void authenticate_shouldReturnAuthResponse_whenAuthenticationIsSuccessful() {
    // Arrange
    AuthRequest authRequest = new AuthRequest("test@example.com", "password");
    Authentication authentication = mock(Authentication.class);
    User user = new User(); // Set up the User object as needed

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(authentication.getName()).thenReturn(authRequest.email());
    when(userService.findUserByEmail(authRequest.email())).thenReturn(user);
    when(jwtUtil.createToken(user)).thenReturn("mockToken");
    when(jwtConfig.getTokenExpirationAfterSeconds()).thenReturn(3600L);

    // Act
    AuthResponse authResponse = underTest.authenticate(authRequest);

    // Assert
    assertEquals(authRequest.email(), authResponse.email());
    assertEquals("mockToken", authResponse.accessToken());
    assertEquals(3600, authResponse.expiresInSeconds());
    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userService).findUserByEmail(authRequest.email());
    verify(jwtUtil).createToken(user);
  }

  @Test
  void
      authenticate_shouldThrowBadCredentialsAuthenticationException_whenBadCredentialsExceptionIsThrown() {
    // Arrange
    AuthRequest authRequest = new AuthRequest("test@example.com", "wrongPassword");

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Bad credentials"));

    // Act & Assert
    assertThrows(
        BadCredentialsAuthenticationException.class, () -> underTest.authenticate(authRequest));

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userService, never()).findUserByEmail(anyString());
    verify(jwtUtil, never()).createToken(any(User.class));
  }

  @Test
  void authenticate_shouldThrowGeneralAuthenticationException_whenOtherExceptionIsThrown() {
    // Arrange
    AuthRequest authRequest = new AuthRequest("test@example.com", "password");

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new RuntimeException("Unexpected error"));

    // Act & Assert
    assertThrows(GeneralAuthenticationException.class, () -> underTest.authenticate(authRequest));

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userService, never()).findUserByEmail(anyString());
    verify(jwtUtil, never()).createToken(any(User.class));
  }

  @Test
  void registerUser_shouldReturnAuthResponse_whenUserIsSuccessfullyRegistered() {
    // Arrange
    CreateUserDto createUserDto =
        new CreateUserDto("test@example.com", "password", null, null, null, null);
    User user = new User(); // Set up the User object as needed
    Authentication authentication = mock(Authentication.class);
    UserDto userDto =
        new UserDto(
            1L, "test@example.com", "password", null, null, null, null, null, null, null, null);

    when(userService.createUser(createUserDto)).thenReturn(userDto);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(authentication.getName()).thenReturn(createUserDto.email());
    when(userService.findUserByEmail(createUserDto.email())).thenReturn(user);
    when(jwtUtil.createToken(user)).thenReturn("mockToken");
    when(jwtConfig.getTokenExpirationAfterSeconds()).thenReturn(3600L);

    // Act
    AuthResponse authResponse = underTest.registerUser(createUserDto);

    // Assert
    assertEquals(createUserDto.email(), authResponse.email());
    assertEquals("mockToken", authResponse.accessToken());
    assertEquals(3600, authResponse.expiresInSeconds());
    verify(userService).createUser(createUserDto);
    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(userService).findUserByEmail(createUserDto.email());
    verify(jwtUtil).createToken(user);
  }
}
