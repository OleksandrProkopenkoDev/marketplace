package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.service.AdService;

@Service
@RequiredArgsConstructor
@Transactional
public class AdServiceImpl implements AdService {

  @Override
  public Page<Ad> findAll(Pageable pageable) {
    return null;
  }
}
