package ua.tc.marketplace.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tc.marketplace.exception.tag.TagInUseException;
import ua.tc.marketplace.exception.tag.TagNotFoundException;
import ua.tc.marketplace.exception.user.UserNotFoundException;
import ua.tc.marketplace.model.dto.tag.CreateTagDto;
import ua.tc.marketplace.model.dto.tag.TagDto;
import ua.tc.marketplace.model.dto.tag.UpdateTagDto;
import ua.tc.marketplace.model.entity.Tag;
import ua.tc.marketplace.repository.ArticleRepository;
import ua.tc.marketplace.repository.TagRepository;
import ua.tc.marketplace.service.TagService;
import ua.tc.marketplace.util.mapper.TagMapper;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link TagService} interface. Provides methods for creating, retrieving,
 * updating, and deleting tags.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final ArticleRepository articleRepository;

    /**
     * Retrieves a paginated list of all tags.
     *
     * @param pageable Pagination information (page number, size, sorting).
     * @return A page of TagDto objects.
     */
    @Override
    public Page<TagDto> findAll(Pageable pageable) {
        Page<Tag> tags = tagRepository.findAll(pageable);
        return tags.map(tagMapper::toDto);
    }

    /**
     * Retrieves a tag by their ID.
     *
     * @param id The ID of the tag to retrieve.
     * @return The TagDto representing the tag.
     * @throws TagNotFoundException If the Tag is not found.
     */
    @Override
    public TagDto findTagById(Long id) {
        Tag tag = getTag(id);
        return tagMapper.toDto(tag);
    }

    /**
     * Updates an existing user.
     *
     * @param id           The id of the tag to update.
     * @param updateTagDto The DTO containing updated tag information.
     * @return The updated TagDto.
     * @throws TagNotFoundException If the tag to update is not found.
     */
    @Transactional
    @Override
    public TagDto updateTag(Long id, @NonNull UpdateTagDto updateTagDto) {
        Tag existingTag = getTag(id);
        tagMapper.updateEntityFromDto(existingTag, updateTagDto);
        return tagMapper.toDto(tagRepository.save(existingTag));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws UserNotFoundException If the user to delete is not found.
     */
    @Transactional
    @Override
    public void deleteTagById(Long id) {
        log.info("Deleting tag with id={}", id);
        Tag existingTag = getTag(id);
        if (articleRepository.getTagCountById(id)>0){
            throw new TagInUseException(id);
        } else {
            tagRepository.deleteById(existingTag.getId());
        }
    }

    /**
     * Creates a new tag.
     *
     * @param createTagDto The DTO containing tag information for creation.
     * @return The created TagDto.
     */
    @Transactional
    @Override
    public TagDto createTag(CreateTagDto createTagDto) {
        Optional<Tag> existingTag = tagRepository.getByName(createTagDto.name());
        if (!existingTag.isEmpty()) return tagMapper.toDto(existingTag.get());
        Tag tag = tagMapper.toEntity(createTagDto);
        return tagMapper.toDto(tagRepository.save(tag));
    }

    /**
     * Retrieves a tag by their ID, throwing a TagNotFoundException if not found.
     *
     * @param id The ID of the tag to retrieve.
     * @return The found Tag entity.
     * @throws TagNotFoundException If the tag is not found.
     */
    private Tag getTag(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    }
}
