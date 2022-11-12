package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.model.dto.UserSaveDto;
import ru.clevertec.ecl.model.entity.User;

import java.util.List;

public interface UserService {

    UserDto findUserDtoById(Long id);

    UserDto saveRecovery(UserSaveDto userSaveDto);

    UserMaxSaleDto findMostUsedTagByUser();

    List<User> findByFirstIdAndLastId(Long firstId, Long lastId);

    User saveRecovery(User user);

}
