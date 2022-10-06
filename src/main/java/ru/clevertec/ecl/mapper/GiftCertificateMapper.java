package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificatePutDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TagMapper.class)
public interface GiftCertificateMapper {

    @Mapping(source = "localDateTime", target = "createDate")
    @Mapping(source = "localDateTime", target = "lastUpdateDate")
    @Mapping(source = "tag", target = "tag")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntity(GiftCertificatePutDto giftCertificateDto, LocalDateTime localDateTime, List<Tag> tag);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "tag", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate toEntityPut(Long id, GiftCertificatePutDto giftCertificateDto,
                                @MappingTarget GiftCertificate giftCertificate
    );

    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> toDtoList(List<GiftCertificate> giftCertificateList);

}
