package ua.tc.marketplace.service;

import ua.tc.marketplace.model.entity.User;

public interface UserService {

  User findUserById(Long userId);
}
