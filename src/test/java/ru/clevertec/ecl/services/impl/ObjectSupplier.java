package ru.clevertec.ecl.services.impl;

import ru.clevertec.ecl.model.dto.*;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ObjectSupplier {

    public static GiftCertificate getGiftCertificate() {
        ArrayList<Tag> objects = new ArrayList<>();
        objects.add(Tag.builder().name("tag").build());
        return GiftCertificate.builder()
                .id(1L)
                .name("name")
                .price(BigDecimal.valueOf(10.10))
                .description("description")
                .tag(objects)
                .duration(1)
                .build();
    }

    public static GiftCertificateUpdateDto getGiftCertificatePutDto() {
        ArrayList<String> stringName = new ArrayList<>();
        stringName.add("tag");
        return GiftCertificateUpdateDto.builder()
                .name("name")
                .price(BigDecimal.valueOf(10.10))
                .description("description")
                .tagNames(stringName)
                .duration(1)
                .build();
    }

    public static GiftCertificateCreateDto getGiftCertificateCreateDto() {
        ArrayList<String> stringName = new ArrayList<>();
        stringName.add("tag");
        return GiftCertificateCreateDto.builder()
                .name("name")
                .price(BigDecimal.valueOf(10.10))
                .description("description")
                .tagNames(stringName)
                .duration(1)
                .build();
    }

    public static GiftCertificateDto getGiftCertificateDto() {
        return GiftCertificateDto.builder()
                .id(1L)
                .name("name")
                .price(BigDecimal.valueOf(10.10))
                .description("description")
                .duration(1)
                .build();
    }

    public static Tag getTag() {
        return Tag.builder()
                .id(1L)
                .name("tag")
                .build();
    }

    public static TagDto getTagDto() {
        return TagDto.builder()
                .id(1L)
                .name("tag").build();
    }

    public static TagUpdateDto getTagPutDto() {
        return TagUpdateDto.builder()
                .name("tag").build();
    }

    public static TagCreateDto getTagCreateDto() {
        return TagCreateDto.builder()
                .name("tag").build();
    }

    public static User getUser() {
        return User.builder()
                .id(1L)
                .name("name")
                .orders(List.of(new Order()))
                .build();
    }

    public static UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .name("name")
                .build();
    }

    public static Order getOrder() {
        return Order.builder()
                .id(1L)
                .createDate(LocalDateTime.of(2022, 10, 8, 12, 30))
                .giftCertificate(new GiftCertificate())
                .price(BigDecimal.valueOf(10.98))
                .user(new User())
                .build();
    }

    public static OrderDto getOrderDto() {
        return OrderDto.builder()
                .id(1L)
                .createDate(LocalDateTime.of(2022, 10, 8, 12, 30))
                .price(BigDecimal.valueOf(10.98))
                .build();
    }

    public static OrderInformationDto getOrderInformationDto() {
        return OrderInformationDto.builder()
                .createDate(LocalDateTime.of(2022, 10, 8, 12, 30))
                .price(BigDecimal.valueOf(10.98))
                .build();
    }
}
