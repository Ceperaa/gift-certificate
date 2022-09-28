package ru.clevertec.ecl.services;

import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagForCreateDto;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    TagDto findTagDtoById(Long id);

    void delete(Long id);

    TagDto update(Map<String, Object> giftCertificateMap, Long id);

    List<TagDto> findAll(PageRequest page);

    TagDto saveTagDto(TagForCreateDto tagForCreateDto);
}
