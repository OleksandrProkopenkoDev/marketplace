package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Tag;

/**
 * Repository interface for managing {@link Tag} entities.
 *
 * <p>This interface extends {@link JpaRepository},
 * providing the CRUD operations for {@link Tag} entities with {@code Long} as the ID type.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
//  Optional<User> findByEmail(String username);
}
