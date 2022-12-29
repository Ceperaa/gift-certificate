package ru.clevertec.ecl.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCommitLogDto {

    private Long id;
    private LocalDateTime createDate;
    private BigDecimal price;
    private GiftCertificateDto giftCertificate;
    private UserDto user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserDto {

        Long id;
        String name;
        String surname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class GiftCertificateDto {

        Long id;
        String name;
        String description;
        BigDecimal price;
        Integer duration;
        List<TagDto> tag;
    }
}
