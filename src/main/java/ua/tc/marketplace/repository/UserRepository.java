package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
