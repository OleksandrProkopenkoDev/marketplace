package ua.tc.marketplace.util.ad_filtering.filters;

import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.util.ad_filtering.FilterSpecification;

/**
 * CategoryFilterSpecification is a component that implements the FilterSpecification interface for
 * the Ad entity.
 *
 * <p>This class provides a JPA Specification for filtering ads based on their category. It creates
 * a query that matches ads where the category ID is equal to the specified value.
 */
@Component("category")
public class CategoryFilterSpecification implements FilterSpecification<Ad> {

  @Override
  public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
    return (root, query, cb) ->
        cb.equal(root.get("category").get("id"), Long.valueOf(filterCriteria.getValue()));
  }
}
