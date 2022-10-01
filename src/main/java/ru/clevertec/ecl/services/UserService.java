package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserForCreateDto;

import java.util.Map;

public interface UserService {

    UserDto findUserDtoById(Long id);

    void delete(Long id);

    UserDto update(Map<String, Object> giftCertificateMap, Long id);

    UserDto saveUserDto(UserForCreateDto tagForCreateDto);


}
