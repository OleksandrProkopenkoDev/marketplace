package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tc.marketplace.model.entity.Comment;

/**
 * Repository interface for managing {@link Comment} entities.
 *
 * <p>This interface extends {@link JpaRepository},
 * providing the CRUD operations for {@link Comment} entities with {@code Long} as the ID type.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
