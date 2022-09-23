package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagForCreateDto;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {

    Tag toEntity(TagForCreateDto tagDto);

    TagDto toDto(Tag tag);

    List<TagDto> toDtoList(List<Tag> tag);
}
