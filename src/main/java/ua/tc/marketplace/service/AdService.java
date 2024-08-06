package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;

/**
 * Service interface defining operations for managing advertisements. Includes methods for
 * retrieving, creating, updating, and deleting advertisements.
 */
public interface AdService {

  Page<AdDto> findAll(Pageable pageable);

  AdDto findAdById(Long adId);

  AdDto createNewAd(CreateAdDto dto);

  AdDto updateAd(Long adId, UpdateAdDto dto);

  void deleteAd(Long adId);
}
