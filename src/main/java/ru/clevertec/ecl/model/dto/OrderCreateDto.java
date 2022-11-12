package ru.clevertec.ecl.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateDto {

    Long id;
    Long userId;
    Long giftCertificateId;
}
