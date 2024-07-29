package ua.tc.marketplace.util.ad_filtering.filters;

import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.util.ad_filtering.FilterSpecification;

/**
 * TitleFilterSpecification is a component that implements the FilterSpecification interface for the
 * Ad entity.
 *
 * <p>This class is used to create a JPA Specification for filtering ads based on their title. It
 * generates a "like" query that matches ads whose title contains the specified value.
 */
@Component("title")
public class TitleFilterSpecification implements FilterSpecification<Ad> {

  @Override
  public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
    return (root, query, cb) -> cb.like(root.get("title"), "%" + filterCriteria.getValue() + "%");
  }
}
