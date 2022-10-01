package ru.clevertec.ecl.services.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.ObjectNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserForCreateDto;
import ru.clevertec.ecl.model.entity.Users;
import ru.clevertec.ecl.model.entity.Users_;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.services.UserService;
import ru.clevertec.ecl.services.EntityService;

import java.util.Map;

// TODO: 30.09.2022 2. Добавить нового пользователя объекта. Create User
//         реализовать только операции получения для пользовательского объекта. implement only get operations for user entity.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, EntityService<Users> {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDto findUserDtoById(Long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    public Users findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id));
    }

    @Override
    @Transactional
    @SneakyThrows(JsonMappingException.class)
    public UserDto update(Map<String, Object> map, Long id) {
        map.put(Users_.ID, id);
        Users user = new ObjectMapper().updateValue(findById(id), map);
        return mapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto saveUserDto(UserForCreateDto userDto) {
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }
}
