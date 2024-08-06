package ua.tc.marketplace.util.ad_filtering.filters;

import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.util.ad_filtering.FilterSpecification;

/**
 * DescriptionFilterSpecification is a component that implements the FilterSpecification interface
 * for the Ad entity.
 *
 * <p>This class provides a JPA Specification for filtering ads based on their description. It
 * creates a query that matches ads with a description containing the specified substring.
 */
@Component("description")
public class DescriptionFilterSpecification implements FilterSpecification<Ad> {

  @Override
  public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
    return (root, query, cb) ->
        cb.like(root.get("description"), "%" + filterCriteria.getValue() + "%");
  }
}
