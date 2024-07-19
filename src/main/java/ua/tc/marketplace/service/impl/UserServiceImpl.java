package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.repository.UserRepository;
import ua.tc.marketplace.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User findUserById(Long userId) {

    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }
}
