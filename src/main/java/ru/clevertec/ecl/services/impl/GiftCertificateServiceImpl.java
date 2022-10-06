package ru.clevertec.ecl.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificatePutDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.services.GiftCertificateService;
import ru.clevertec.ecl.services.TagCreateCertificate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMapper mapper;
    private final TagCreateCertificate tagService;


    public GiftCertificateDto findById(Long id) {
        return mapper
                .toDto(findGiftCertificateById(id));
    }

    private GiftCertificate findGiftCertificateById(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(GiftCertificateDto.class, id));
    }

    @Transactional
    public void delete(Long id) {
        giftCertificateRepository.delete(findGiftCertificateById(id));
    }

    @Transactional
    public GiftCertificateDto update(GiftCertificatePutDto giftCertificateDto, Long id) {
        GiftCertificate giftCertificateById = findGiftCertificateById(id);
        giftCertificateById.getTag().addAll(tagService.saveTagIfNotExists(giftCertificateDto.getTagNames()));
        GiftCertificate giftCertificate = mapper.toEntityPut(id, giftCertificateDto, giftCertificateById);
        return mapper.toDto(giftCertificateRepository.save(giftCertificate));
    }

    public List<GiftCertificateDto> findAll(PageRequest page) {
        return mapper.toDtoList(
                giftCertificateRepository.findAll((page)).toList());
    }

    @Transactional
    public GiftCertificateDto create(GiftCertificatePutDto giftCertificateDto) {
        return mapper.toDto(giftCertificateRepository
                .save(mapper.toEntity(
                        giftCertificateDto,
                        LocalDateTime.now().minusNanos(0),
                        tagService.mapToTagList(giftCertificateDto.getTagNames())))
        );
    }

    private ExampleMatcher matcherConfig() {
        ExampleMatcher.GenericPropertyMatcher propertyMatcher = ExampleMatcher
                .GenericPropertyMatchers
                .ignoreCase()
                .stringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return ExampleMatcher.matchingAll()
                .withMatcher("tag", propertyMatcher)
                .withMatcher("description", propertyMatcher);
    }

    public List<GiftCertificateDto> findByCertificateNameAndDescription(String name, String description, PageRequest page) {
        Page<GiftCertificate> certificateDtoPage = giftCertificateRepository
                .findAll(Example.of(GiftCertificate
                                .builder()
                                .name(name)
                                .description(description)
                                .build(),
                        matcherConfig()),
                        page);
        return mapper.toDtoList(certificateDtoPage.toList());
    }

    public List<GiftCertificateDto> findByTagName(String name, PageRequest page) {
        return mapper.toDtoList(giftCertificateRepository.findByName(name, page));
    }
}
