package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.model.dto.UserPutDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, EntityService<User> {

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

    @Transactional
    public UserDto update(UserPutDto userPutDto, Long id) {
        User users = mapper.toPutEntity(id, findById(id), userPutDto);
        return mapper.toDto(userRepository.save(users));
    }

    @Override
    @Transactional
    public UserDto saveUserDto(UserPutDto userDto) {
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));
    }

    @Override
    public UserMaxSaleDto findMostUsedTagByUser() {
        return userRepository.findByMaxSaleAngMaxUseTag();
    }
}
