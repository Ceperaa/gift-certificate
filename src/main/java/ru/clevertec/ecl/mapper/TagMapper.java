package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagForPutDto;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {

    Tag toEntity(TagForPutDto tagDto);

    @Mapping(source = "id", target = "id")
    Tag toPutEntity(Long id, TagForPutDto tagDto);

    TagDto toDto(Tag tag);

    List<TagDto> toDtoList(List<Tag> tag);

}
