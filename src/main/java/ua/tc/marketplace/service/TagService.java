package ua.tc.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.tc.marketplace.model.dto.tag.CreateTagDto;
import ua.tc.marketplace.model.dto.tag.TagDto;

public interface TagService {

  Page<TagDto> findAll(Pageable pageable);

  TagDto findTagById(Long id);

  TagDto createTag(CreateTagDto createTagDto);

  TagDto updateTag(Long id, TagDto updateTagDto);

  void deleteTagById(Long id);
}
