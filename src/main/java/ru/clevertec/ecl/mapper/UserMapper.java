package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserForCreateDto;
import ru.clevertec.ecl.model.entity.Users;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(Users user);

    Users toEntity(UserForCreateDto user);
}
