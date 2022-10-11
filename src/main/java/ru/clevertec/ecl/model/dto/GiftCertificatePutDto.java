package ru.clevertec.ecl.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificatePutDto {

    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @NotEmpty
    @Size(min = 2, max = 100)
    String description;

    @Positive
    @DecimalMin("00.01")
    @DecimalMax("100.00")
    BigDecimal price;

    @Min(1)
    @Max(30)
    Integer duration;

    @Size(max = 30)
    List<String> tagNames;
}
