package ru.clevertec.ecl.services;

import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateForCreateDto;
import ru.clevertec.ecl.model.dto.TagDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GiftCertificateService {


     List<GiftCertificateDto> findGiftCertificateByTags(String[] tags, PageRequest page);

     GiftCertificateDto findGiftCertificateDtoById(Long id);

     void delete(Long id);

     GiftCertificateDto updatePrice(BigDecimal price, Long id);

     GiftCertificateDto update(Map<String,Object> giftCertificateMap,Long id);

     List<GiftCertificateDto> findAll(PageRequest page);

     List<GiftCertificateDto> findByCertificateName(String name, String description, PageRequest page);

     GiftCertificateDto create(GiftCertificateForCreateDto giftCertificate);

     List<GiftCertificateDto> findByTagName(String tagName, PageRequest pageRequest);
}
