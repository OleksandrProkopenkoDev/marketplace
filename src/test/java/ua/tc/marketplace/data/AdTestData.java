package ua.tc.marketplace.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ua.tc.marketplace.exception.tests.FailedLoadJsonResourceException;
import ua.tc.marketplace.model.dto.ad.AdAttributeDto;
import ua.tc.marketplace.model.dto.ad.AdAttributeRequestDto;
import ua.tc.marketplace.model.dto.ad.AdDto;
import ua.tc.marketplace.model.dto.ad.CreateAdDto;
import ua.tc.marketplace.model.dto.ad.UpdateAdDto;
import ua.tc.marketplace.model.entity.Ad;
import ua.tc.marketplace.model.entity.AdAttribute;
import ua.tc.marketplace.model.entity.Attribute;
import ua.tc.marketplace.model.entity.Category;
import ua.tc.marketplace.model.entity.User;
import ua.tc.marketplace.model.enums.ValueType;
import ua.tc.marketplace.utils.TestUtils;

/**
 * AdTestData is a utility class that provides test data for various Ad-related entities and DTOs.
 *
 * <p>This class contains static methods to generate sample data used in unit tests for Ad, AdDto,
 * CreateAdDto, UpdateAdDto, and related attribute classes.
 *
 * <p>This class is intended to be used as a helper in unit tests to provide consistent and reusable
 * test data. It is marked as `final` and has a private constructor to prevent instantiation, as it
 * is designed to be used statically.
 *
 * <p>The `getAdAttributeJson()` method reads a JSON file using the `TestUtils.readResourceFile`
 * method and throws a custom `FailedLoadJsonResourceException` if an error occurs during file
 * reading.
 */
public final class AdTestData {

  private AdTestData() {
    throw new UnsupportedOperationException(
        "AdTestData is a utility class and cannot be instantiated");
  }

  public static List<Attribute> getAttributes() {
    return List.of(
        new Attribute(1L, "breed", ValueType.STRING),
        new Attribute(2L, "age", ValueType.STRING),
        new Attribute(3L, "size", ValueType.STRING),
        new Attribute(4L, "gender", ValueType.STRING),
        new Attribute(5L, "coat length", ValueType.STRING),
        new Attribute(6L, "color", ValueType.STRING),
        new Attribute(7L, "health condition", ValueType.STRING),
        new Attribute(8L, "pet name", ValueType.STRING));
  }

  public static List<AdAttribute> getUpdatedAdAttributes(Ad ad) {
    return List.of(
        new AdAttribute(null, ad, getAttributes().get(0), "bulldog"),
        new AdAttribute(null, ad, getAttributes().get(1), "10 years"),
        new AdAttribute(null, ad, getAttributes().get(2), "Big"),
        new AdAttribute(null, ad, getAttributes().get(3), "Male"),
        new AdAttribute(null, ad, getAttributes().get(4), "Medium"),
        new AdAttribute(null, ad, getAttributes().get(5), "brown"),
        new AdAttribute(null, ad, getAttributes().get(6), "Healthy"),
        new AdAttribute(null, ad, getAttributes().get(7), "Rocky"));
  }

  public static List<AdAttributeDto> getUpdatedAttributeDtos() {
    return List.of(
        new AdAttributeDto("breed", "bulldog"),
        new AdAttributeDto("age", "10 years"),
        new AdAttributeDto("size", "Big"),
        new AdAttributeDto("gender", "Male"),
        new AdAttributeDto("coat length", "Medium"),
        new AdAttributeDto("color", "brown"),
        new AdAttributeDto("health condition", "Healthy"),
        new AdAttributeDto("pet name", "Rocky"));
  }

  public static List<AdAttributeDto> getAttributeDtos() {
    return List.of(
        new AdAttributeDto("breed", "Retriever"),
        new AdAttributeDto("age", "2 years"),
        new AdAttributeDto("size", "Big"),
        new AdAttributeDto("gender", "Male"),
        new AdAttributeDto("coat length", "Medium"),
        new AdAttributeDto("color", "Grey"),
        new AdAttributeDto("health condition", "Healthy"),
        new AdAttributeDto("pet name", "Rocky"));
  }

  public static List<AdAttributeRequestDto> getAdAttributeRequestDtos() {
    return List.of(
        new AdAttributeRequestDto(1L, "bulldog"),
        new AdAttributeRequestDto(2L, "10 years"),
        new AdAttributeRequestDto(6L, "brown"));
  }

  public static AdDto getAdDto(List<AdAttributeDto> adAttributeDtos) {
    return new AdDto(
        1L,
        1L,
        "Sample Ad",
        "This is a sample ad",
        BigDecimal.valueOf(100.00),
        Collections.emptyList(),
        null,
        1L,
        adAttributeDtos,
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public static Ad getMockUpdatedAd(
      Long adId, User mockUser, UpdateAdDto updateAdDto, Category mockCategory) {
    return new Ad(
        adId,
        mockUser,
        updateAdDto.title(),
        updateAdDto.description(),
        updateAdDto.price(),
        null, // Assuming no photos in this test case
        null, // Assuming no thumbnail in this test case
        mockCategory,
        new ArrayList<>(),
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public static AdDto getMockUpdatedAdDto(Long adId, UpdateAdDto updateAdDto) {
    return new AdDto(
        adId,
        updateAdDto.authorId(),
        updateAdDto.title(),
        updateAdDto.description(),
        updateAdDto.price(),
        null, // Assuming no photos in this test case
        null, // Assuming no thumbnail in this test case
        updateAdDto.categoryId(),
        getUpdatedAttributeDtos(),
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public static UpdateAdDto getUpdateAdDto() {
    return new UpdateAdDto(
        1L, // Mock author ID
        "Updated Title",
        "Updated Description",
        BigDecimal.valueOf(200.00),
        2L, // Updated Category ID
        getAdAttributeRequestDtos());
  }

  public static CreateAdDto getCreateAdDto(MultipartFile[] files) {
    return new CreateAdDto(
        1L,
        "Sample Ad",
        "This is a sample ad",
        BigDecimal.valueOf(100.00),
        files,
        getAdAttributeJson(),
        1L);
  }

  public static String getAdAttributeJson() {
    String fileName = "ad-attributes.json";
    try {
      return TestUtils.readResourceFile(fileName);
    } catch (IOException e) {
      throw new FailedLoadJsonResourceException(fileName);
    }
  }
}
