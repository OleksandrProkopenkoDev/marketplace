package ua.tc.marketplace.service.impl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.model.dto.user.CreateUserDto;
import ua.tc.marketplace.model.dto.user.UpdateUserDto;
import ua.tc.marketplace.model.dto.user.UserDto;
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
    Page<User> users = userRepository.findAll(pageable);
    return users.stream()
        .collect(Collectors.collectingAndThen(Collectors.toList(), PageImpl::new))
        .map(userMapper::toDto);
  }

  @Override
  public UserDto findUserById(Long id) {
    User user = getUser(id);
    return userMapper.toDto(user);
  }

  @Transactional
  @Override
  public UserDto createUser(CreateUserDto createUserDto) {
    User user = userMapper.toEntity(createUserDto);
    user.setPassword(passwordEncoder.encode(createUserDto.password()));
//    user.setCreatedAt(LocalDateTime.now());
    return userMapper.toDto(
        userRepository.save(user));
  }

  @Transactional
  @Override
  public UserDto updateUser(@NonNull UpdateUserDto updateUserDto) {
    User existingUser = getUser(updateUserDto.id());
    userMapper.updateEntityFromDto(existingUser, updateUserDto);
//    existingUser.setUpdatedAt(LocalDateTime.now());
    return userMapper.toDto(userRepository.save(existingUser));
  }

  @Transactional
  @Override
  public void deleteUserById(Long id) {
    User existingUser = getUser(id);
    userRepository.deleteById(existingUser.getId());
  }

  private User getUser (Long id){
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
  }


}
