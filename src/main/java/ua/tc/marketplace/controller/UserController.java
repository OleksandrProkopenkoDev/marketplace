package ua.tc.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tc.marketplace.model.dto.UserDto;
import ua.tc.marketplace.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/all")
  public ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getAllUsers(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
  }

  @PostMapping()
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userDto));
  }

  @PutMapping()
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUserById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


}
