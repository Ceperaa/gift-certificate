package ru.clevertec.ecl.services.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.ObjectNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagForCreateDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.services.TagCreateCertificate;
import ru.clevertec.ecl.services.TagService;

import java.util.List;
import java.util.Map;
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
                .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id));
    }

    private Optional<Tag> findTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.delete(findTagById(id));
    }

    @Transactional
    @SneakyThrows(JsonMappingException.class)
    public TagDto update(Map<String, Object> map, Long id) {
        map.put("id", id);
        Tag tag = new ObjectMapper().updateValue(findTagById(id), map);
        return mapper.toDto(tagRepository.save(tag));
    }

    public List<TagDto> findAll(PageRequest page) {
        return mapper
                .toDtoList(tagRepository.findAll(page).toList());
    }

    public TagDto saveTagDto(TagForCreateDto tagDto) {
        return mapper.toDto(saveTag(mapper.toEntity(tagDto)));
    }

    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> mapToTagList(List<String> tagNameList) {
        return tagNameList
                .stream()
                .map((tag) -> findTagByName(tag)
                        .orElseGet(() -> saveTag(Tag.builder().name(tag).build())))
                .collect(Collectors.toList());
    }
}
