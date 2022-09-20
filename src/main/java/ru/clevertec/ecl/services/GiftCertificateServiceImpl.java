package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.exception.ObjectNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.util.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final ru.clevertec.ecl.repository.RepositorySearch<GiftCertificate> giftCertificateRepository;
    private final GiftCertificateMapper mapper;

    public GiftCertificateDto findById(Long id) {
        return mapper
                .entityToDto(giftCertificateRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id))
                );
    }

    public void delete(Long id) {
        giftCertificateRepository.deleteById(id);
    }

    public GiftCertificateDto update(Map<String, Object> giftCertificateMap, Long id) {
        return mapper
                .entityToDto(giftCertificateRepository.updateFieldsById(giftCertificateMap, id)
                        .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id))
                );
    }

    public List<GiftCertificateDto> findAll(Page page) {
        return mapper
                .ToDtoList(giftCertificateRepository.findAll(page));
    }

    public GiftCertificateDto create(GiftCertificateDto giftCertificate) {
        giftCertificate.setCreateDate(LocalDateTime.now().minusNanos(0));
        giftCertificate.setLastUpdateDate(LocalDateTime.now().minusNanos(0));
        return mapper.
                entityToDto(
                        giftCertificateRepository.create(mapper.dtoToEntity(giftCertificate))
                );
    }

    public List<GiftCertificateDto> findByTag(String tagName) {
        return mapper.ToDtoList(giftCertificateRepository.findByTagName(tagName));
    }

    public List<GiftCertificateDto> findByTagName(String name, String description) {
        return mapper.ToDtoList(giftCertificateRepository.findByTagNameOrDescription(name, description));
    }
}
