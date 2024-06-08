package ua.tc.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.tc.marketplace.model.dto.DemoRequest;
import ua.tc.marketplace.model.entity.Demo;
import ua.tc.marketplace.repository.DemoRepository;
import ua.tc.marketplace.service.DemoService;

@RequiredArgsConstructor
@Service
public class DemoServiceImpl implements DemoService {

  private final DemoRepository demoRepository;

  @Override
  public Page<DemoRequest> findAll(Pageable pageable) {

    return demoRepository.findAll(pageable).map(this::toDto);
  }

  private DemoRequest toDto(Demo demo) {
    return new DemoRequest(demo.getName());
  }
}
