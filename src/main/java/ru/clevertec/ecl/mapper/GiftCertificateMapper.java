package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GiftCertificateMapper {

    GiftCertificate dtoToEntity(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto entityToDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> ToDtoList(List<GiftCertificate> giftCertificateList);
}
