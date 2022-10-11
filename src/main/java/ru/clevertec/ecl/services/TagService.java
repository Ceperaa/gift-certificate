package ru.clevertec.ecl.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagPutDto;

import java.util.List;

public interface TagService {

    TagDto findTagDtoById(Long id);

    void delete(Long id);

    TagDto update(TagPutDto tagForCreateDto, Long id);

    List<TagDto> findAll(Pageable page);

    TagDto saveTagDto(TagPutDto tagForCreateDto);
}
