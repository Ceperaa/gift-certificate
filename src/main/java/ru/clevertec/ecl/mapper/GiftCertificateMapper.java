package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.model.dto.CertificatePriceDto;
import ru.clevertec.ecl.model.dto.GiftCertificateCreateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateUpdateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TagMapper.class)
public interface GiftCertificateMapper {

    @Mapping(source = "tag", target = "tag")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntity(GiftCertificateUpdateDto giftCertificateDto, List<Tag> tag);

    @Mapping(source = "tag", target = "tag")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntity(GiftCertificateCreateDto giftCertificateDto, List<Tag> tag);

    @Mapping(source = "id", target = "id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntityPut(Long id, GiftCertificateUpdateDto giftCertificateDto,
                                @MappingTarget GiftCertificate giftCertificate
    );

    GiftCertificate toEntity(GiftCertificateDto giftCertificateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntity(CertificatePriceDto giftCertificateDto, @MappingTarget GiftCertificate certificate);

    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    @Mapping(target = "tagNames", ignore = true)
    GiftCertificateCreateDto toDto(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> toDtoList(List<GiftCertificate> giftCertificateList);

}
