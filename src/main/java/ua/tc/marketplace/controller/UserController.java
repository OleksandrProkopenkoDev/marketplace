package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.tc.marketplace.model.dto.user.UpdateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.openapi.UserOpenApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController implements UserOpenApi {

  private final UserService userService;

  @Override
  @GetMapping("/all")
  public ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findUserDtoById(id));
  }

  @PutMapping()
  public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UpdateUserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUserById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


}
