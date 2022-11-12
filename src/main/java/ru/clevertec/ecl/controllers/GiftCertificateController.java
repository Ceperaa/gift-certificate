package ru.clevertec.ecl.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.model.dto.CertificatePriceDto;
import ru.clevertec.ecl.model.dto.GiftCertificateCreateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateUpdateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.GiftCertificateService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("v1/gift-certificates")
@Validated
@Slf4j
public class GiftCertificateController extends AbstractController<GiftCertificate> {

    private final GiftCertificateService service;

    public GiftCertificateController(EntityService<GiftCertificate> entityService, GiftCertificateService service) {
        super(entityService);
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> add(@RequestBody @Valid GiftCertificateCreateDto giftCertificateDto) {
        log.debug("GiftCertificate - adding");
        return new ResponseEntity<>(service.createGiftCertificateDto(giftCertificateDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/recovery")
    @ApiIgnore
    public ResponseEntity<GiftCertificateDto> recovery(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        log.debug("GiftCertificate - adding");
        return new ResponseEntity<>(service.saveRecovery(giftCertificateDto),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> update(@RequestBody @Valid GiftCertificateUpdateDto giftCertificatePutDto,
                                                     @PathVariable @Positive Long id) {
        return new ResponseEntity<>(service.update(giftCertificatePutDto, id),
                HttpStatus.CREATED);
    }

    @PatchMapping("/price/{id}")
    public ResponseEntity<GiftCertificateDto> updatePrice(@RequestBody CertificatePriceDto certificatePriceDto,
                                                          @PathVariable @Positive Long id) {
        return new ResponseEntity<>(service.updatePrice(certificatePriceDto, id),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long id) {
        service.delete(id);
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> all(Pageable pageRequest) {
        return new ResponseEntity<>(service.findAll(pageRequest),
                HttpStatus.OK);
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

    @GetMapping("/by-tag")
    public ResponseEntity<List<GiftCertificateDto>> findByTag(
            @RequestParam(required = false) @Size(min = 2, max = 30) String tagName,
            @PageableDefault Pageable pageRequest) {
        return new ResponseEntity<>(service.findByTagName(tagName, pageRequest),
                HttpStatus.OK);
    }

    @GetMapping("/by-tags")
    ResponseEntity<List<GiftCertificateDto>> findGiftCertificateByTags(
           @RequestParam(required = false) List<@Size(min = 2, max = 30) String> tags, Pageable pageRequest) {
        return new ResponseEntity<>(service.findGiftCertificateByTags(tags, pageRequest),
                HttpStatus.OK);
    }
}
