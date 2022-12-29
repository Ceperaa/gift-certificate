package ru.clevertec.ecl.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.TagCreateDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagUpdateDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.TagService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("v1/tags")
@Validated
@Slf4j
public class TagController extends AbstractController<Tag>{

    private final TagService tagService;

    public TagController(EntityService<Tag> entityService, TagService tagService) {
        super(entityService);
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagDto> add(@RequestBody @Valid TagCreateDto tagCreateDto) {
        log.debug("saving user");
        return new ResponseEntity<>(tagService.saveTagDto(tagCreateDto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> update(@RequestBody @Valid TagUpdateDto tagPutDto,
                                         @PathVariable @Positive Long id) {
        return new ResponseEntity<>(tagService.update(tagPutDto, id),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> all(Pageable pageRequest) {
        return new ResponseEntity<>(tagService.findAll(pageRequest),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@PathVariable @Positive Long id) {
        return new ResponseEntity<>(tagService.findTagDtoById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long id) {
        tagService.delete(id);
    }

    @PostMapping("/recovery")
    @ApiIgnore
    public ResponseEntity<Tag> recovery(@RequestBody @Valid Tag tag) {
        log.debug("Tag - adding");
        return new ResponseEntity<>(tagService.saveRecovery(tag),
                HttpStatus.CREATED);
    }
}
