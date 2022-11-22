package ru.clevertec.ecl.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.util.LocalDateStringConvert;
import ru.clevertec.ecl.util.StringLocalDateConvert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftCertificateDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<TagDto> tag;

    @JsonSerialize(converter = LocalDateStringConvert.class)
    @JsonDeserialize(converter = StringLocalDateConvert.class)
    private LocalDateTime createDate;

    @JsonSerialize(converter = LocalDateStringConvert.class)
    @JsonDeserialize(converter = StringLocalDateConvert.class)
    private LocalDateTime lastUpdateDate;
}
