package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.model.dto.CertificatePriceDto;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificatePutDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.repository.CustomerSpecifications;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.services.EntityService;
import ru.clevertec.ecl.services.GiftCertificateService;
import ru.clevertec.ecl.services.TagCreateCertificate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService, EntityService<GiftCertificate> {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMapper mapper;
    private final TagCreateCertificate tagService;

    public GiftCertificateDto findGiftCertificateDtoById(Long id) {
        return mapper
                .toDto(findById(id));
    }

    public GiftCertificate findById(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GiftCertificateDto.class, id));
    }

    @Transactional
    public void delete(Long id) {
        giftCertificateRepository.delete(findById(id));
    }

    @Override
    public GiftCertificateDto updatePrice(CertificatePriceDto certificatePriceDto, Long id) {
        return mapper.toDto(save(mapper.toEntity(certificatePriceDto, findById(id))));
    }

    @Transactional
    public GiftCertificateDto update(GiftCertificatePutDto giftCertificateDto, Long id) {
        GiftCertificate giftCertificateById = findById(id);
        giftCertificateById.getTag().addAll(tagService.saveTagIfNotExists(giftCertificateDto.getTagNames()));
        GiftCertificate giftCertificate = mapper.toEntityPut(id, giftCertificateDto, giftCertificateById);
        return mapper.toDto(giftCertificateRepository.save(giftCertificate));
    }

    public List<GiftCertificateDto> findAll(Pageable page) {
        return mapper.toDtoList(
                giftCertificateRepository.findAll((page)).toList());
    }

    @Transactional
    public GiftCertificateDto createGiftCertificateDto(GiftCertificatePutDto giftCertificateDto) {
        return mapper.toDto(save(mapper.toEntity(
                giftCertificateDto,
                tagService.createTagIfNotExists(giftCertificateDto.getTagNames())))
        );
    }

    private GiftCertificate save(GiftCertificate giftCertificateDto) {
        return giftCertificateRepository.save(giftCertificateDto);
    }

    private ExampleMatcher matcherConfig() {
        ExampleMatcher.GenericPropertyMatcher propertyMatcher = ExampleMatcher
                .GenericPropertyMatchers
                .ignoreCase()
                .stringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return ExampleMatcher.matchingAll()
                .withMatcher("name", propertyMatcher)
                .withMatcher("description", propertyMatcher);
    }

    public List<GiftCertificateDto> findByCertificateNameAndDescription(
            String name,
            String description,
            Pageable page) {
        return mapper.toDtoList(giftCertificateRepository
                .findAll(Example.of(GiftCertificate
                                .builder()
                                .name(name)
                                .description(description)
                                .build(),
                        matcherConfig()),
                        page).toList());
    }

    public List<GiftCertificateDto> findByTagName(String name, Pageable page) {
        return mapper.toDtoList(giftCertificateRepository.findByName(name, page));
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificateByTags(List<String> tags, Pageable page) {
        return mapper.toDtoList(giftCertificateRepository
                .findAll(CustomerSpecifications.byMultipleTags(tags), page).toList());
    }
}
