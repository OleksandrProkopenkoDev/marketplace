package ua.tc.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tc.marketplace.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
