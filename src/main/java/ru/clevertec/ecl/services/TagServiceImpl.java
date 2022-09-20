package ru.clevertec.ecl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.exception.ObjectNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.RepositoryCrud;
import ru.clevertec.ecl.util.Page;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final RepositoryCrud<Tag> tagRepository;
    private final TagMapper mapper;

    public TagDto findById(Long id) {
        return mapper
                .toDto(tagRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id))
                );
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    public TagDto update(Map<String, Object> giftCertificateMap, Long id) {
        return mapper
                .toDto(tagRepository.updateFieldsById(giftCertificateMap, id)
                        .orElseThrow(() -> new ObjectNotFoundException(GiftCertificateDto.class, id))
                );
    }

    public List<TagDto> findAll(Page page) {
        return mapper
                .toDtoList(tagRepository.findAll(page));
    }

    public TagDto create(TagDto giftCertificate) {
        return mapper.
                toDto(tagRepository.create(mapper.toEntity(giftCertificate)));
    }
}
