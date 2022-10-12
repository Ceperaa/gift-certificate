package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagPutDto;
import ru.clevertec.ecl.services.TagService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> add(@RequestBody @Valid TagPutDto tagDto) {
        return new ResponseEntity<>(tagService.saveTagDto(tagDto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> update(@RequestBody @Valid TagPutDto tagPutDto,
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
}
