package ru.clevertec.ecl.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// TODO: 30.09.2022 5. Получить информацию о заказе пользователя: стоимость и время покупки.
//         *Стоимость заказа не должна изменяться при изменении цены подарочного сертификата.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderInformationDto {

    BigDecimal price;
    LocalDateTime createDate;
}
