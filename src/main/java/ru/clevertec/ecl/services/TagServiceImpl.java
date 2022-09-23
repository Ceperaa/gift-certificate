package ru.clevertec.ecl.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper mapper;

    public TagDto findTagDtoById(Long id) {
        return mapper.toDto(findTagById(id));
    }

    private Tag findTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id));
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.delete(findTagById(id));
    }

    @Transactional
    public TagDto update(Map<String, Object> map, Long id) {
        map.put("id", id);
        Tag tag = new ObjectMapper().convertValue(map, Tag.class);
        return mapper
                .toDto(tagRepository.save(tag));
    }

    public List<TagDto> findAll(PageRequest page) {
        return mapper
                .toDtoList(tagRepository.findAll(page).toList());
    }

    public TagDto saveTagDto(TagForCreateDto tagDto) {
        return mapper.toDto(saveTag(mapper.toEntity(tagDto)));
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
