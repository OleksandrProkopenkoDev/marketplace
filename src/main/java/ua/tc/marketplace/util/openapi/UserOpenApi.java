package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.tc.marketplace.controller.UserController;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.dto.user.UpdateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;

/**
 * This interface defines the OpenAPI annotations for the {@link UserController} class. It provides endpoints
 * for managing users.
 */
@Tag(name = "User API", description = "API for managing users")
public interface UserOpenApi {

  @Operation(
      summary = "Get all users",
      description = "Retrieves a pageable list of all users.")
  @GetMapping("/all")
  ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault Pageable pageable);

  @Operation(
      summary = "Get advertisement by ID",
      description = "Retrieves an advertisement by its unique identifier.")
  @GetMapping("/{id}")
  ResponseEntity<UserDto> getUserById(@PathVariable Long id);

  @Operation(
      summary = "Create a new user",
      description = "Creates a new user based on the provided data.")
  @PostMapping
  ResponseEntity<UserDto> createUser( @Valid @RequestBody CreateUserDto userDto);

  @Operation(
      summary = "Updates an existing user",
      description = "Updates an existing user with the provided data.")
  @PutMapping()
  ResponseEntity<UserDto> updateUser(@RequestBody @Valid UpdateUserDto userDto);

  @Operation(
      summary = "Delete a user",
      description = "Deletes a user by its unique identifier.")
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteUser(@PathVariable Long id);
}
