package ua.tc.marketplace.facade;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;

/**
 * AdFacade defines the interface for operations related to ads within the system.
 *
 * <p>This interface includes methods for querying, creating, updating, and deleting ads. It
 * provides a high-level abstraction for ad management, allowing for interaction with ad entities
 * through data transfer objects (DTOs).
 */
public interface AdFacade {
  Page<AdDto> findAll(Map<String, String> filterCriteria, Pageable pageable);

  AdDto findAdById(Long adId);

  AdDto createNewAd(CreateAdDto dto);

  AdDto updateAd(Long adId, UpdateAdDto dto);

  void deleteAd(Long adId);
}
