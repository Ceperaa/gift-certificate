package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.services.TagService;
import ru.clevertec.ecl.util.Page;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto add(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto update(
            @RequestBody Map<String, Object> batch,
            @PathVariable Long id) {
        return tagService.update(batch, id);
    }

    @GetMapping
    public List<TagDto> all(
            @RequestParam(required = false) int limit,
            @RequestParam(required = false) int offset
    ) {
        return tagService.findAll(Page.of(limit,offset));
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable long id) {
        return tagService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
