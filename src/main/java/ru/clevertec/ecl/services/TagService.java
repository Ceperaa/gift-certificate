package ru.clevertec.ecl.services;

import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.util.Page;

import java.util.List;
import java.util.Map;

public interface TagService {

    TagDto findById(Long id);

    void delete(Long id);

    TagDto update(Map<String, Object> giftCertificateMap, Long id);

    List<TagDto> findAll(Page page);

    TagDto create(TagDto giftCertificate);
}
