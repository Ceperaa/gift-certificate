package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.util.Page;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {

     GiftCertificateDto findById(Long id);

     void delete(Long id);

     GiftCertificateDto update(Map<String,Object> giftCertificateMap,Long id);

     List<GiftCertificateDto> findAll(Page page);

     GiftCertificateDto create(GiftCertificateDto giftCertificate);

     List<GiftCertificateDto> findByTag(String tagName);

     List<GiftCertificateDto> findByTagName(String name, String description);
}
