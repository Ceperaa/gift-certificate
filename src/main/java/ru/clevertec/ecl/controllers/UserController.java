package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.services.UserService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable @Positive Long id) {
        return new ResponseEntity<>(userService.findUserDtoById(id),
                HttpStatus.OK);
    }

    @GetMapping("most-used-tag")
    ResponseEntity<UserMaxSaleDto> findMostUsedTagByUser() {
        return new ResponseEntity<>(userService.findMostUsedTagByUser(),
                HttpStatus.OK);
    }
}
