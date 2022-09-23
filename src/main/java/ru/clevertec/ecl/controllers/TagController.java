package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagForCreateDto;
import ru.clevertec.ecl.services.TagService;
import ru.clevertec.ecl.util.ValidatorHandler;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final ValidatorHandler validatorHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto add(
            @RequestBody @Valid TagForCreateDto tagDto,
            BindingResult bindingResult
    ) throws ValidationException {
        validatorHandler.message(bindingResult);
        return tagService.saveTagDto(tagDto);
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
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return tagService.findAll(PageRequest.of(limit, offset));
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable long id) {
        return tagService.findTagDtoById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
