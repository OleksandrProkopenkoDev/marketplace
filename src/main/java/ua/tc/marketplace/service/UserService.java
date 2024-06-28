package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.model.dto.UserDto;


public interface UserService {

  Page<UserDto> findAll(Pageable pageable);

  UserDto createUser(UserDto createUserDto);
  UserDto findById(Long id);
  UserDto updateUser(UserDto updateUserDto);
  void deleteUserById(Long id);
}
