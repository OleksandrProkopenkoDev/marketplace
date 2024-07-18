package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.entity.Ad;

public interface AdService {

  Page<Ad> findAll(Pageable pageable);
}
