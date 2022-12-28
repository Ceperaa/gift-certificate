package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toDto(User user);
}
