package ua.tc.marketplace.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.User;

/**
 * Repository interface for managing {@link User} entities.
 *
 * <p>This interface extends {@link org.springframework.data.jpa.repository.JpaRepository},
 * providing the CRUD operations for {@link User} entities with {@code Long} as the ID type.
 */
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String username);
}
