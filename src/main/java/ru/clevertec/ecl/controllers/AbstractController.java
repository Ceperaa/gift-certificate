package ru.clevertec.ecl.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.ecl.services.EntityService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

public abstract class AbstractController<T> {

    private final EntityService<T> entityService;

    public AbstractController(EntityService<T> entityService) {
        this.entityService = entityService;
    }

    @GetMapping("/sequence")
    @ApiIgnore
    public Long getSequence() {
        return entityService.getSequence();
    }

    @GetMapping("/range")
    @ApiIgnore
    public ResponseEntity<List<T>> findByFirstIdAndLastId(@RequestParam Long firstId,
                                                          @RequestParam Long lastId) {
        return new ResponseEntity<>(entityService.findByFirstIdAndLastId(firstId, lastId),
                HttpStatus.OK);
    }

    @GetMapping("/sequence/nextval")
    @ApiIgnore
    public Long sequenceNextval(){
        return entityService.nextval();
    }
}
