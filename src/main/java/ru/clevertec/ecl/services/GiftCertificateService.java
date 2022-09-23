package ru.clevertec.ecl.services;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateForCreateDto;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {

     GiftCertificateDto findById(Long id);

     void delete(Long id);

     GiftCertificateDto update(Map<String,Object> giftCertificateMap,Long id) throws JsonMappingException;

     List<GiftCertificateDto> findAll(PageRequest page);

     GiftCertificateDto create(GiftCertificateForCreateDto giftCertificate);

     List<GiftCertificateDto> findByTag(String tagName, Pageable pageRequest);
}
