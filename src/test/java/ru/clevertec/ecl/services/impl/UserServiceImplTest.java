package ru.clevertec.ecl.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class UserServiceImplTest {

    private final UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;

    private User users;
    private UserDto userDto;

    UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepository, mapper);
    }

    @BeforeEach
    void setUp() {
        users = ObjectSupplier.getUser();
        userDto = ObjectSupplier.getUserDto();
    }

    @Test
    void findUserDtoById() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users));
        given(mapper.toDto(users)).willReturn(userDto);
        UserDto userDtoById = userService.findUserDtoById(1L);
        assertEquals(userDtoById, userDto);
    }

    @Test
    void findById() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users));
        User userDtoById = userService.findById(1L);
        assertEquals(userDtoById, users);
    }
}