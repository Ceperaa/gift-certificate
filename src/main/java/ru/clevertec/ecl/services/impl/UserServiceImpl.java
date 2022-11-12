package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.model.dto.UserSaveDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.UserService;

import java.util.List;

import static ru.clevertec.ecl.util.Constant.USER_SEQ;

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
    public UserDto saveRecovery(UserSaveDto userSaveDto) {
        return mapper.toDto(userRepository.save(mapper.toEntity(userSaveDto)));
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

    @Override
    public Long getSequence() {
        return userRepository.getValMySequence();
    }

    @Override
    public List<User> findByFirstIdAndLastId(Long firstId, Long lastId) {
        return userRepository.findByIdBetween(firstId,lastId);
    }

    @Override
    @Transactional
    public Long nextval() {
        return userRepository.nextval(USER_SEQ);
    }

    @Override
    @Transactional
    public User saveRecovery(User user) {
        userRepository.setval(USER_SEQ,user.getId());
        User save = userRepository.save(user);
        return save;
    }
}
