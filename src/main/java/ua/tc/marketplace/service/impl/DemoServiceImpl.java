package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.repository.DemoRepository;
import ua.tc.marketplace.service.DemoService;
import ua.tc.marketplace.util.mapper.DemoMapper;

@RequiredArgsConstructor
@Service
public class DemoServiceImpl implements DemoService {

  private final DemoRepository demoRepository;
  private final DemoMapper demoMapper;

  @Override
  public Page<DemoRequest> findAll(Pageable pageable) {

    return demoRepository.findAll(pageable).map(demoMapper::toDto);
  }
}
