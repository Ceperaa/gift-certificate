package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;

public interface UserService {

    UserDto findUserDtoById(Long id);

    UserMaxSaleDto findMostUsedTagByUser();


}
