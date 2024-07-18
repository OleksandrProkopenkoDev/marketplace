package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.service.AdService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ad")
public class AdController {
  private final AdService adService;

  @GetMapping("/{adId}")
  public ResponseEntity<AdDto> getAdById(@PathVariable Long adId) {
    return ResponseEntity.ok(adService.findAdById(adId));
  }

  @PostMapping
  public ResponseEntity<AdDto> createNewAd(@ModelAttribute @Valid CreateAdDto dto) {
    return ResponseEntity.ok(adService.createNewAd(dto));
  }

  @PutMapping("/{adId}")
  public ResponseEntity<AdDto> updateAd(@PathVariable Long adId, @RequestBody UpdateAdDto dto) {
    return ResponseEntity.ok(adService.updateAd(adId, dto));
  }

  @DeleteMapping("/{adId}")
  public ResponseEntity<Void> deleteAd(@PathVariable Long adId){
    adService.deleteAd(adId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
