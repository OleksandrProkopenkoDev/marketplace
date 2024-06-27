package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.DemoRequest;


public interface DemoService {

  Page<DemoRequest> findAll(Pageable pageable);

  DemoRequest findById(Integer id);
}
