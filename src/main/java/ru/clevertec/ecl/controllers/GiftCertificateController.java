package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.services.GiftCertificateService;
import ru.clevertec.ecl.util.Page;
import ru.clevertec.ecl.util.Sorting;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gift-certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto add(@RequestBody GiftCertificateDto giftCertificateDto) {
        return service.create(giftCertificateDto);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto update(@RequestBody Map<String, Object> map, @PathVariable Long id) {
        return service.update(map, id);
    }

    @GetMapping
    public List<GiftCertificateDto> all(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            Sorting sorting
    ) {
        return service.findAll(Page.of(limit, offset, sorting));
    }

    @GetMapping("/tag")
    public List<GiftCertificateDto> findByTag(@RequestParam String tagName) {
        return service.findByTag(tagName);
    }

    @GetMapping("/nameOrDescription")
    public List<GiftCertificateDto> findByName(
            @RequestParam String name,
            @RequestParam String description) {
        return service.findByTagName(name, description);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateDto findById(@PathVariable long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
