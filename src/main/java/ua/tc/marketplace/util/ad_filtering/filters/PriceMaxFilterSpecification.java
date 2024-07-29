package ua.tc.marketplace.util.ad_filtering.filters;

import java.math.BigDecimal;
import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.util.ad_filtering.FilterSpecification;

/**
 * PriceMaxFilterSpecification is a component that implements the FilterSpecification interface for
 * the Ad entity.
 *
 * <p>This class is used to create a JPA Specification for filtering ads based on a maximum price.
 * It generates a query that matches ads with a price less than or equal to the specified value.
 */
@Component("priceMax")
public class PriceMaxFilterSpecification implements FilterSpecification<Ad> {

  @Override
  public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
    return (root, query, cb) ->
        cb.lessThanOrEqualTo(root.get("price"), new BigDecimal(filterCriteria.getValue()));
  }
}
