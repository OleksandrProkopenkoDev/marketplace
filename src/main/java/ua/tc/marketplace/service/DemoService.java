package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.DemoRequest;

/**
 * Service interface for managing {@link DemoRequest} entities.
 *
 * <p>This interface defines methods for retrieving {@link DemoRequest} entities, including finding
 * all entities with pagination and finding a specific entity by its ID.
 */
public interface DemoService {

  Page<DemoRequest> findAll(Pageable pageable);
}
