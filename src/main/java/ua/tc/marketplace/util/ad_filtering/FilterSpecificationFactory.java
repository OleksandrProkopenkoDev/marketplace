package ua.tc.marketplace.util.ad_filtering;

import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.Ad;

@Component
@RequiredArgsConstructor
public class FilterSpecificationFactory {

  private final Map<String, FilterSpecification<Ad>> filters;

  public Specification<Ad> getSpecification(Map<String, String> filterCriteria) {
    return filterCriteria.entrySet().stream()
        .filter(entry -> entry.getValue() != null)
        .map(
            entry -> {
              String key = entry.getKey();
              FilterSpecification<Ad> filter =
                  key.startsWith("attribute_") ? filters.get("attribute") : filters.get(key);
              return filter != null ? filter.getSpecification(entry) : null;
            })
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }
}
