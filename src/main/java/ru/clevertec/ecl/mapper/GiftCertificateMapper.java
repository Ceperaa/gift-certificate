package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.model.dto.CertificatePriceDto;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificatePutDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TagMapper.class)
public interface GiftCertificateMapper {

    @Mapping(source = "tag", target = "tag")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntity(GiftCertificatePutDto giftCertificateDto, List<Tag> tag);

    @Mapping(source = "id", target = "id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntityPut(Long id, GiftCertificatePutDto giftCertificateDto,
                                @MappingTarget GiftCertificate giftCertificate
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntity(CertificatePriceDto giftCertificateDto, @MappingTarget GiftCertificate certificate);

    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> toDtoList(List<GiftCertificate> giftCertificateList);

}
