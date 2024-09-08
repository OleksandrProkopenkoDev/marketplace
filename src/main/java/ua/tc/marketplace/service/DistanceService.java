package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.entity.Location;

public interface DistanceService {

  Page<AdDto> calculateDistance(Location location1, Page<AdDto> adDtoPage);

}
