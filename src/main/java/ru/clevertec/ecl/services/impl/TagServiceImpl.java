package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.TagCreateDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagUpdateDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.services.TagCreateCertificate;
import ru.clevertec.ecl.services.TagService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService, TagCreateCertificate {

    private final TagRepository tagRepository;
    private final TagMapper mapper;

    public TagDto findTagDtoById(Long id) {
        return mapper.toDto(findTagById(id));
    }

    private Tag findTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GiftCertificateDto.class, id));
    }

    public Optional<Tag> findTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.delete(findTagById(id));
    }

    @Transactional
    public TagDto update(TagUpdateDto tagForCreateDto, Long id) {
        Tag giftCertificate = mapper.toPutEntity(id, findTagById(id), tagForCreateDto);
        return mapper.toDto(tagRepository.save(giftCertificate));
    }

    public List<TagDto> findAll(Pageable page) {
        return mapper.toDtoList(tagRepository.findAll(page).toList());
    }

    public TagDto saveTagDto(TagCreateDto tagDto) {
        return mapper.toDto(saveTag(mapper.toEntity(tagDto)));
    }

    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> createTagIfNotExists(List<String> tagNameList) {
        return tagNameList
                .stream()
                .map((tag) -> findTagByName(tag)
                        .orElseGet(() -> saveTag(Tag.builder().name(tag).build())))
                .collect(Collectors.toList());
    }

    public List<Tag> saveTagIfNotExists(List<String> tagNameList) {
        return tagNameList
                .stream()
                .filter(tag1 -> findTagByName(tag1).isEmpty())
                .map((tag) -> saveTag(Tag.builder().name(tag).build()))
                .collect(Collectors.toList());
    }
}
