package ua.tc.marketplace.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import ua.tc.marketplace.model.entity.Ad;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tc.marketplace.service.AdService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ad")
public class AdController {
  private final AdService adService;

  @GetMapping
  public ResponseEntity<Page<Ad>> getAllAds(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(adService.findAll(pageable));
  }
}
