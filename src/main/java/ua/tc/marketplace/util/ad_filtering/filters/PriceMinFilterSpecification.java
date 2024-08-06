package ua.tc.marketplace.util.ad_filtering.filters;

import java.math.BigDecimal;
import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.util.ad_filtering.FilterSpecification;

/**
 * PriceMinFilterSpecification is a component that implements the FilterSpecification interface for
 * the Ad entity.
 *
 * <p>This class is used to create a JPA Specification for filtering ads based on a minimum price.
 * It generates a query that matches ads with a price greater than or equal to the specified value.
 */
@Component("priceMin")
public class PriceMinFilterSpecification implements FilterSpecification<Ad> {

  @Override
  public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
    return (root, query, cb) ->
        cb.greaterThanOrEqualTo(root.get("price"), new BigDecimal(filterCriteria.getValue()));
  }
}
