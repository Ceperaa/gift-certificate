package ru.clevertec.ecl.services;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.exception.ObjectNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateForCreateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.repository.GiftCertificateRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMapper mapper;
    private final TagService tagService;

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
        giftCertificateMap.put("id", id);
        GiftCertificate giftCertificate = new ObjectMapper().updateValue(findGiftCertificateById(id),
                giftCertificateMap);
        return mapper.toDto(giftCertificateRepository.save(giftCertificate));
    }

    public List<GiftCertificateDto> findAll(PageRequest page) {
        return mapper.
                toDtoList(giftCertificateRepository.findAll((page)).toList());
    }

    @Transactional
    public GiftCertificateDto create(GiftCertificateForCreateDto giftCertificateDto) {
        return mapper.
                toDto(giftCertificateRepository
                        .save(mapper.toEntity(
                                giftCertificateDto,
                                LocalDateTime.now().minusNanos(0),
                                tagService.mapToTagList(giftCertificateDto.getTagNames())))
                );
    }

    public List<GiftCertificateDto> findByTag(String Name, Pageable page) {
        return mapper.toDtoList(giftCertificateRepository.findByNameWithPagination(Name, page));
    }
}
