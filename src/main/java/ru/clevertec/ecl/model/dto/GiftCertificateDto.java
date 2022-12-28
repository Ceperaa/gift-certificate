package ru.clevertec.ecl.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.clevertec.ecl.util.LocalDateStringConvert;
import ru.clevertec.ecl.util.StringLocalDateConvert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateDto {

    Long id;
    String name;
    String description;
    BigDecimal price;
    Integer duration;
    List<TagDto> tag;

    @JsonSerialize(converter = LocalDateStringConvert.class)
    @JsonDeserialize(converter = StringLocalDateConvert.class)
    LocalDateTime createDate;

    @JsonSerialize(converter = LocalDateStringConvert.class)
    @JsonDeserialize(converter = StringLocalDateConvert.class)
    LocalDateTime lastUpdateDate;
}
