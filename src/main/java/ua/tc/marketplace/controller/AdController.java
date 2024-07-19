package ua.tc.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import ua.tc.marketplace.util.openapi.AdOpenApi;

/**
 * Controller class for managing advertisements via REST API endpoints. Handles operations such as
 * retrieving, creating, updating, and deleting advertisements.
 *
 * <p>Endpoints:
 *
 * <ul>
 *   <li>GET /api/v1/ad: Retrieves a pageable list of all advertisements.
 *   <li>GET /api/v1/ad/{adId}: Retrieves an advertisement by its unique identifier.
 *   <li>POST /api/v1/ad: Creates a new advertisement based on the provided data.
 *   <li>PUT /api/v1/ad/{adId}: Updates an existing advertisement with the provided data.
 *   <li>DELETE /api/v1/ad/{adId}: Deletes an advertisement by its unique identifier.
 * </ul>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ad")
public class AdController implements AdOpenApi {
  private final AdService adService;

  @Override
  @GetMapping
  public ResponseEntity<Page<AdDto>> getAllAds(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(adService.findAll(pageable));
  }

  @Override
  @GetMapping("/{adId}")
  public ResponseEntity<AdDto> getAdById(@PathVariable Long adId) {
    return ResponseEntity.ok(adService.findAdById(adId));
  }

  @Override
  @PostMapping
  public ResponseEntity<AdDto> createNewAd(@ModelAttribute @Valid CreateAdDto dto) {
    return ResponseEntity.ok(adService.createNewAd(dto));
  }

  @Override
  @PutMapping("/{adId}")
  public ResponseEntity<AdDto> updateAd(@PathVariable Long adId, @RequestBody UpdateAdDto dto) {
    return ResponseEntity.ok(adService.updateAd(adId, dto));
  }

  @Override
  @DeleteMapping("/{adId}")
  public ResponseEntity<Void> deleteAd(@PathVariable Long adId) {
    adService.deleteAd(adId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
