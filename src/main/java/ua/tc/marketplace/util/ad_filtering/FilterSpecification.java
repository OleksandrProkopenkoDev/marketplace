package ua.tc.marketplace.util.ad_filtering;
import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;

public interface FilterSpecification<T> {
  Specification<T> getSpecification(Entry<String, String> filterCriteria);

}

