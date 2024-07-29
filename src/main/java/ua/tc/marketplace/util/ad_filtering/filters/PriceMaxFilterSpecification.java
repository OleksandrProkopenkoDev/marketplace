package ua.tc.marketplace.util.ad_filtering.filters;

import java.math.BigDecimal;
import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.util.ad_filtering.FilterSpecification;

@Component("priceMax")
public class PriceMaxFilterSpecification implements FilterSpecification<Ad> {

  @Override
  public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
    return (root, query, cb) ->
        cb.lessThanOrEqualTo(root.get("price"), new BigDecimal(filterCriteria.getValue()));
  }
}
