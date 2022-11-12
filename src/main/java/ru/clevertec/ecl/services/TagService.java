package ru.clevertec.ecl.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.TagCreateDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagUpdateDto;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;

public interface TagService {

    TagDto findTagDtoById(Long id);

    void delete(Long id);

    TagDto update(TagUpdateDto tagForCreateDto, Long id);

    List<TagDto> findAll(Pageable page);

    TagDto saveTagDto(TagCreateDto tagForCreateDto);

    Tag saveRecovery(Tag user);

}
