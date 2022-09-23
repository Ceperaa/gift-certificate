package ru.clevertec.ecl.controllers;

import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateForCreateDto;
import ru.clevertec.ecl.services.GiftCertificateService;
import ru.clevertec.ecl.util.Sorting;
import ru.clevertec.ecl.util.ValidatorHandler;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/gift-certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService service;
    private final ValidatorHandler validatorHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto add(
            @RequestBody @Valid GiftCertificateForCreateDto giftCertificateDto,
            BindingResult bindingResult
    ) throws ValidationException {
        validatorHandler.message(bindingResult);
        return service.create(giftCertificateDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto update(@RequestBody Map<String, Object> map, @PathVariable Long id)
            throws JsonMappingException {
        return service.update(map, id);
    }

    @GetMapping
    public List<GiftCertificateDto> all(
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        return service.findAll(PageRequest.of(offset, limit));
    }

    @GetMapping("/tag")
    public List<GiftCertificateDto> findByTag(
            @RequestParam(defaultValue = "") String tagName,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "NAME") Sorting sorts
    ) {
        return service.findByTag(tagName,
                PageRequest.of(
                        offset,
                        limit,
                        Sort.by(Sort.Direction.ASC, sorts.getSort())
                ));
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
