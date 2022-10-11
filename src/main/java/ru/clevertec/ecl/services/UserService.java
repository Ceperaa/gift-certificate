package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.model.dto.UserPutDto;

public interface UserService {

    UserDto findUserDtoById(Long id);

    void delete(Long id);

    UserDto update(UserPutDto userPutDto, Long id);

    UserDto saveUserDto(UserPutDto tagForCreateDto);

    UserMaxSaleDto findMostUsedTagByUser();


}
