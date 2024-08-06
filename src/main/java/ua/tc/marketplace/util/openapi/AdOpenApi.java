package ua.tc.marketplace.util.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;

/**
 * This interface defines the OpenAPI annotations for the AdController class. It provides endpoints
 * for managing advertisements.
 */
@Tag(name = "Advertisement API", description = "API for managing advertisements")
public interface AdOpenApi {

  @Operation(
      summary = "Get all advertisements",
      description = "Retrieves a pageable list of all advertisements.")
  @GetMapping
  ResponseEntity<Page<AdDto>> getAllAds(
      @RequestParam Map<String, String> params, @PageableDefault(sort = "id") Pageable pageable);

  @Operation(
      summary = "Get advertisement by ID",
      description = "Retrieves an advertisement by its unique identifier.")
  @GetMapping("/{adId}")
  ResponseEntity<AdDto> getAdById(@PathVariable Long adId);

  @Operation(
      summary = "Create a new advertisement",
      description = "Creates a new advertisement based on the provided data.")
  @PostMapping
  ResponseEntity<AdDto> createNewAd(@ModelAttribute @Valid CreateAdDto dto);

  @Operation(
      summary = "Update an existing advertisement",
      description = "Updates an existing advertisement with the provided data.")
  @PutMapping("/{adId}")
  ResponseEntity<AdDto> updateAd(@PathVariable Long adId, @RequestBody UpdateAdDto dto);

  @Operation(
      summary = "Delete an advertisement",
      description = "Deletes an advertisement by its unique identifier.")
  @DeleteMapping("/{adId}")
  ResponseEntity<Void> deleteAd(@PathVariable Long adId);
}
