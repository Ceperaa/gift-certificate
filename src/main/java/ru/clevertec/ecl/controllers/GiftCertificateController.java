package ru.clevertec.ecl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.CertificatePriceDto;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificatePutDto;
import ru.clevertec.ecl.services.GiftCertificateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("v1/gift-certificates")
@RequiredArgsConstructor
@Validated
public class GiftCertificateController {

    private final GiftCertificateService service;

    @PostMapping
    public ResponseEntity<GiftCertificateDto> add(@RequestBody @Valid GiftCertificatePutDto giftCertificateDto) {
        return new ResponseEntity<>(service.createGiftCertificateDto(giftCertificateDto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> update(@RequestBody @Valid GiftCertificatePutDto giftCertificatePutDto,
                                                     @PathVariable @Positive Long id) {
        return new ResponseEntity<>(service.update(giftCertificatePutDto, id),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> all(Pageable pageRequest) {
        return new ResponseEntity<>(service.findAll(pageRequest),
                HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<GiftCertificateDto>> findByTag(
            @RequestParam(required = false) @Size(min = 2, max = 30) String tagName,
            @PageableDefault Pageable pageRequest) {
        return new ResponseEntity<>(service.findByTagName(tagName,
                pageRequest), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<GiftCertificateDto>> findByNameAndDescription(
            @RequestParam(required = false) @Size(min = 2, max = 30) String name,
            @RequestParam(required = false) @Size(min = 2, max = 30) String description,
            Pageable pageable) {
        return new ResponseEntity<>(service.findByCertificateNameAndDescription(name, description, pageable),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable @Positive Long id) {
        return new ResponseEntity<>(service.findGiftCertificateDtoById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/price/{id}")
    public ResponseEntity<GiftCertificateDto> updatePrice(@RequestBody CertificatePriceDto certificatePriceDto,
                                                          @PathVariable @Positive Long id) {
        return new ResponseEntity<>(service.updatePrice(certificatePriceDto, id),
                HttpStatus.CREATED);
    }

    @GetMapping("/by-tags")
    ResponseEntity<List<GiftCertificateDto>> findGiftCertificateByTags(String[] tags, Pageable pageRequest) {
        return new ResponseEntity<>(service.findGiftCertificateByTags(tags, pageRequest),
                HttpStatus.OK);
    }
}
