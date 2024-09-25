package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.tc.marketplace.model.entity.Article;
import ua.tc.marketplace.model.entity.Tag;

/**
 * Repository interface for managing {@link Article} entities.
 *
 * <p>This interface extends {@link JpaRepository},
 * providing the CRUD operations for {@link Article} entities with {@code Long} as the ID type.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
//    @Query("select count(*) from article_tag where tag_id =:id")
//    Integer getTagCountById(Long id);
}
