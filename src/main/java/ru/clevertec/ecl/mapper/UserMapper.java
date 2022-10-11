package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserPutDto;
import ru.clevertec.ecl.model.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserPutDto user);

    @Mapping(source = "id", target = "id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toPutEntity(Long id, @MappingTarget User tag, UserPutDto tagDto);
}
