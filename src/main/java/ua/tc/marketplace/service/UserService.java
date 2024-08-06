package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.dto.user.UpdateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;


public interface UserService {

  Page<UserDto> findAll(Pageable pageable);

  UserDto createUser(CreateUserDto createUserDto);
  UserDto findUserById(Long id);
  UserDto updateUser(UpdateUserDto updateUserDto);
  void deleteUserById(Long id);
}
