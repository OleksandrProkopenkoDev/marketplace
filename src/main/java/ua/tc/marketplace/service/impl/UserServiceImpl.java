package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.model.dto.UserDto;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.UserRepository;
import ua.tc.marketplace.service.UserService;
import ua.tc.marketplace.util.mapper.UserMapper;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Page<UserDto> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public UserDto createUser(UserDto createUserDto) {
    createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
    return userMapper.toDto(
        userRepository.save(
            userMapper.toEntity(createUserDto)));
  }

  @Override
  public UserDto findById(Long id) {
    return null;
  }

  @Override
  public UserDto updateUser(UserDto updateUserDto) {
    User existingUser = findById(updateUserDto.getId());
    return null;
  }

  @Override
  public void deleteUserById(Long id) {

  }


}
