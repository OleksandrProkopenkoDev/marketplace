package ua.tc.marketplace.service.impl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.model.dto.UserDto;
import ua.tc.marketplace.model.entity.ContactInfo;
import ua.tc.marketplace.model.entity.Photo;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.model.enums.UserRole;
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

  @Transactional
  @Override
  public UserDto createUser(UserDto createUserDto) {
    createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
    createUserDto.setCreatedAt(LocalDateTime.now());
    return userMapper.toDto(
        userRepository.save(
            userMapper.toEntity(createUserDto)));
  }

  @Override
  public UserDto findById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
    return userMapper.toDto(user);
  }

  @Transactional
  @Override
  public UserDto updateUser(@NonNull UserDto updateUserDto) {
    User existingUser = userRepository.findById(updateUserDto.getId())
        .orElseThrow(() -> new UserNotFoundException(updateUserDto.getId()));

    userMapper.updateEntityFromDto(existingUser, updateUserDto);
    existingUser.setUpdatedAt(LocalDateTime.now());

    return userMapper.toDto(userRepository.save(existingUser));
  }

  @Transactional
  @Override
  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }


}
