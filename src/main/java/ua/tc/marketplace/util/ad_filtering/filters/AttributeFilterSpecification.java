package ua.tc.marketplace.util.ad_filtering.filters;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.AdAttribute;
import ua.tc.marketplace.util.ad_filtering.FilterSpecification;

@Component("attribute")
public class AttributeFilterSpecification implements FilterSpecification<Ad> {

  @Override
  public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
    return (root, query, cb) -> {
      String attributeName = filterCriteria.getKey().substring(10); // Remove "attribute_" prefix
      Join<Ad, AdAttribute> join = root.join("adAttributes", JoinType.LEFT);
      return cb.and(
          cb.equal(join.get("attribute").get("name"), attributeName),
          cb.like(join.get("value"), "%" + filterCriteria.getValue() + "%"));
    };
  }
}
