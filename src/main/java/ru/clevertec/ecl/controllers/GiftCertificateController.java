package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificatePutDto;
import ru.clevertec.ecl.services.GiftCertificateService;
import ru.clevertec.ecl.util.Sorting;
import ru.clevertec.ecl.util.ValidatorHandler;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("api/v1/gift-certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService service;
    private final ValidatorHandler validatorHandler;

    @PostMapping
    public ResponseEntity<GiftCertificateDto> add(
            @RequestBody @Valid GiftCertificatePutDto giftCertificateDto,
            BindingResult bindingResult
    ) throws ValidationException {
        validatorHandler.message(bindingResult);
        return new ResponseEntity<>(service.create(giftCertificateDto),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> update(@RequestBody GiftCertificatePutDto giftCertificateDto,
                                                     @PathVariable Long id) {
        return new ResponseEntity<>(service.update(giftCertificateDto, id), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> all(
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        return new ResponseEntity<>(service.findAll(PageRequest.of(offset, limit)),
                HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<GiftCertificateDto>> findByTag(
            @RequestParam(required = false) String tagName,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset
    ) {
        return new ResponseEntity<>(service.findByTagName(tagName,
                PageRequest.of(
                        offset,
                        limit
                )), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<GiftCertificateDto>> findByNameAndDescription(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "name") Sorting sorts
    ) {
        return new ResponseEntity<>(service.findByCertificateNameAndDescription(name, description,
                PageRequest.of(
                        offset,
                        limit,
                        Sort.by(Sort.Direction.ASC, sorts.getSort())
                )), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
