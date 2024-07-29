package ua.tc.marketplace.util.ad_filtering;
import java.util.Map.Entry;
import org.springframework.data.jpa.domain.Specification;
/**
 * FilterSpecification is an interface that provides a method to generate a JPA Specification based on filter criteria.
 *
 * <p>This interface is designed to be implemented by classes that define how to convert a specific filter criterion
 * (represented as a key-value pair) into a JPA Specification for querying entities of type T.
 *
 * <p>Usage:
 * <ul>
 *   <li>Implement this interface in a class that specifies how to handle the filter criteria for a particular entity type.
 *   <li>Use the getSpecification method to obtain a Specification based on a given filter criterion.
 * </ul>
 *
 * <p>Example:
 * <pre>
 * {@code
 * public class AdFilterSpecification implements FilterSpecification<Ad> {
 *     @Override
 *     public Specification<Ad> getSpecification(Entry<String, String> filterCriteria) {
 *         // Implementation logic to create and return a Specification based on the filter criteria
 *     }
 * }
 * }
 * </pre>
 *
 * @param <T> the type of the entity for which the Specification is to be created
 */

public interface FilterSpecification<T> {
  Specification<T> getSpecification(Entry<String, String> filterCriteria);

}

