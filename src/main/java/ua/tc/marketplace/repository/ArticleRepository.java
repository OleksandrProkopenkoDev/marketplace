package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.tc.marketplace.model.entity.Article;
import ua.tc.marketplace.model.entity.Tag;

/**
 * Repository interface for managing {@link Article} entities.
 *
 * <p>This interface extends {@link JpaRepository},
 * providing the CRUD operations for {@link Article} entities with {@code Long} as the ID type.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) FROM article_tag WHERE tag_id = :tagId")
    Integer getTagCountById(@Param("tagId") Long id);
}
