package ru.clevertec.ecl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// TODO: 30.09.2022 5. Получить информацию о заказе пользователя: стоимость и время покупки.
//         *Стоимость заказа не должна изменяться при изменении цены подарочного сертификата.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInformationDto {

    private BigDecimal price;
    private LocalDateTime createDate;
}
