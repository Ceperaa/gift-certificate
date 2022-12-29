package ru.clevertec.ecl.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.model.dto.UserSaveDto;
import ru.clevertec.ecl.model.entity.User;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.UserService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("v1/users")
@Slf4j
public class UserController extends AbstractController<User> {

    private final UserService userService;

    public UserController(EntityService<User> entityService, UserService userService) {
        super(entityService);
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody @Valid UserSaveDto userSaveDto) {
        log.debug("saving user");
        return new ResponseEntity<>(userService.saveRecovery(userSaveDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/recovery")
    @ApiIgnore
    public ResponseEntity<User> recovery(@RequestBody @Valid User user) {
        log.debug("User - adding");
        user.setOrders(null);
        return new ResponseEntity<>(userService.saveRecovery(user),
                HttpStatus.CREATED);
    }

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
