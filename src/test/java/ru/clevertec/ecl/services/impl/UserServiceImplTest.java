package ru.clevertec.ecl.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserPutDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    private final UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;

    private User users;
    private UserDto userDto;
    private UserPutDto userPutDto;

    UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepository, mapper);
    }

    @BeforeEach
    void setUp() {
        users = ObjectSupplier.getUser();
        userDto = ObjectSupplier.getUserDto();
        userPutDto = ObjectSupplier.getUserPutDto();
    }

    @Test
    void findUserDtoById() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users));
        given(mapper.toDto(users)).willReturn(userDto);
        UserDto userDtoById = userService.findUserDtoById(1L);
        assertEquals(userDtoById, userDto);
    }

    @Test
    void delete() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users));
        userService.delete(1L);
        verify(userRepository).delete(users);
    }

    @Test
    void givenUserDto_whenUpdate() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users));
        given(mapper.toDto(users)).willReturn(userDto);
        given(mapper.toPutEntity(1L, users, userPutDto)).willReturn(users);
        given(userRepository.save(users)).willReturn(users);
        UserDto update = userService.update(userPutDto, 1L);
        assertEquals(update, userDto);
    }

    @Test
    void saveUserDto() {
        given(userRepository.save(users)).willReturn(users);
        given(mapper.toDto(users)).willReturn(userDto);
        given(mapper.toEntity(userPutDto)).willReturn(users);
        UserDto userDto = userService.saveUserDto(userPutDto);
        assertEquals(userDto, userDto);
    }

    @Test
    void findById() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users));
        User userDtoById = userService.findById(1L);
        assertEquals(userDtoById, users);
    }
}