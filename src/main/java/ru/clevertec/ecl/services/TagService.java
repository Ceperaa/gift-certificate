package ru.clevertec.ecl.services;

import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagForPutDto;

import java.util.List;

public interface TagService {

    TagDto findTagDtoById(Long id);

    void delete(Long id);

    TagDto update(TagForPutDto tagForCreateDto, Long id);

    List<TagDto> findAll(PageRequest page);

    TagDto saveTagDto(TagForPutDto tagForCreateDto);

}
