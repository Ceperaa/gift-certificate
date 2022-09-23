package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateForCreateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GiftCertificateMapper {

    @Mapping(source = "localDateTime", target = "createDate")
    @Mapping(source = "localDateTime", target = "lastUpdateDate")
    @Mapping(source = "tags", target = "tags")
    GiftCertificate toEntity(GiftCertificateForCreateDto giftCertificateDto, LocalDateTime localDateTime, List<Tag> tags);

    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> toDtoList(List<GiftCertificate> giftCertificateList);
}
