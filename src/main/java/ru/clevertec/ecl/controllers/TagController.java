package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TagDto> add(
            @RequestBody @Valid TagForCreateDto tagDto,
            BindingResult bindingResult
    ) throws ValidationException {
        validatorHandler.message(bindingResult);
        return new ResponseEntity<>(tagService.saveTagDto(tagDto),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagDto> update(
            @RequestBody Map<String, Object> batch,
            @PathVariable Long id) {
        return new ResponseEntity<>(tagService.update(batch, id),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> all(
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return new ResponseEntity<>(tagService.findAll(PageRequest.of(limit, offset)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(tagService.findTagDtoById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: 30.09.2022           6. Получите наиболее широко используемый тег пользователя с самой высокой стоимостью всех заказов.
    //             *Создайте отдельную конечную точку для этого запроса.
    //         *Продемонстрируйте план выполнения SQL для этого запроса (поясните).
    @GetMapping("most-used-tag/{userId}")
    ResponseEntity<TagDto> findMostUsedTagByUser(Long userId) {
        return new ResponseEntity<>(tagService.findMostUsedTagByUser(userId), HttpStatus.OK);
    }
}
