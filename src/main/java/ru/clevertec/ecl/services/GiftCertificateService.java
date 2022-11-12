package ru.clevertec.ecl.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.CertificatePriceDto;
import ru.clevertec.ecl.model.dto.GiftCertificateCreateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateUpdateDto;

import java.util.List;

public interface GiftCertificateService {


     List<GiftCertificateDto> findGiftCertificateByTags(List<String> tags, Pageable page);

     GiftCertificateDto findGiftCertificateDtoById(Long id);

     void delete(Long id);

     GiftCertificateDto updatePrice(CertificatePriceDto certificatePriceDto, Long id);

     GiftCertificateDto update(GiftCertificateUpdateDto giftCertificateDto, Long id);

     List<GiftCertificateDto> findAll(Pageable page);

     List<GiftCertificateDto> findByCertificateNameAndDescription(String name, String description, Pageable page);

     GiftCertificateDto createGiftCertificateDto(GiftCertificateCreateDto giftCertificate);

     List<GiftCertificateDto> findByTagName(String tagName, Pageable pageRequest);

     GiftCertificateDto saveRecovery(GiftCertificateDto giftCertificate);
}
