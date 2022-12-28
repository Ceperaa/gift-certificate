package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.model.dto.TagCreateDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagUpdateDto;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {

    Tag toEntity(TagCreateDto tagDto);

    @Mapping(source = "id", target = "id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag toPutEntity(Long id, @MappingTarget Tag tag, TagUpdateDto tagDto);

    TagDto toDto(Tag tag);

    List<TagDto> toDtoList(List<Tag> tag);
}
