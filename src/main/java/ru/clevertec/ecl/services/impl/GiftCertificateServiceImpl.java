package ru.clevertec.ecl.services.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.ObjectNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateForCreateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.GiftCertificate_;
import ru.clevertec.ecl.model.entity.Tag_;
import ru.clevertec.ecl.repository.CustomerSpecifications;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.services.GiftCertificateService;
import ru.clevertec.ecl.services.TagCreateCertificate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
                .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id));
    }

    @Transactional
    public void delete(Long id) {
        giftCertificateRepository.delete(findGiftCertificateById(id));
    }

    @Transactional
    @SneakyThrows(JsonMappingException.class)
    public GiftCertificateDto update(Map<String, Object> giftCertificateMap, Long id) {
        giftCertificateMap.put(Tag_.ID, id);
        GiftCertificate giftCertificate = new ObjectMapper().updateValue(findGiftCertificateById(id),
                giftCertificateMap);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return mapper.toDto(giftCertificateRepository.save(giftCertificate));
    }

    public List<GiftCertificateDto> findAll(PageRequest page) {
        return mapper.toDtoList(
                giftCertificateRepository.findAll((page)).toList());
    }

    @Transactional
    public GiftCertificateDto create(GiftCertificateForCreateDto giftCertificateDto) {
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
                .withMatcher(GiftCertificate_.TAG, propertyMatcher)
                .withMatcher(GiftCertificate_.DESCRIPTION, propertyMatcher);
    }

    public List<GiftCertificateDto> findByCertificateName(String name, String description, PageRequest page) {
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

    public List<GiftCertificateDto> findByTagNameAngCertificateName(String name, PageRequest page) {
        return mapper.toDtoList(giftCertificateRepository.findByName(name, page));
    }
}
