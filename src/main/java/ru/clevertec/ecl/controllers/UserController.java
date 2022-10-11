package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.model.dto.UserPutDto;
import ru.clevertec.ecl.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody @Valid UserPutDto tagDto) {
        return new ResponseEntity<>(userService.saveUserDto(tagDto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserPutDto userPutDto,
                                          @PathVariable Long id) {
        return new ResponseEntity<>(userService.update(userPutDto, id),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable @Positive Long id) {
        return new ResponseEntity<>(userService.findUserDtoById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("most-used-tag")
    ResponseEntity<UserMaxSaleDto> findMostUsedTagByUser() {
        return new ResponseEntity<>(userService.findMostUsedTagByUser(),
                HttpStatus.OK);
    }
}
