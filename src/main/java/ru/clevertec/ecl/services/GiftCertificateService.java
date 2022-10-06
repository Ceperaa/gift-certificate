package ru.clevertec.ecl.services;

import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificatePutDto;

import java.util.List;

public interface GiftCertificateService {

     GiftCertificateDto findById(Long id);

     void delete(Long id);

     GiftCertificateDto update(GiftCertificatePutDto giftCertificateDto, Long id);

     List<GiftCertificateDto> findAll(PageRequest page);

     List<GiftCertificateDto> findByCertificateNameAndDescription(String name, String description, PageRequest page);

     GiftCertificateDto create(GiftCertificatePutDto giftCertificate);

     List<GiftCertificateDto> findByTagName(String tagName, PageRequest pageRequest);
}
