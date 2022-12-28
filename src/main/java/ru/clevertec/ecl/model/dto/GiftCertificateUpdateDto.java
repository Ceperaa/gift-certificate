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
public class GiftCertificateUpdateDto {

    @NotBlank
    @Size(min = 2, max = 30)
    String name;

    @NotBlank
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
    List<@Size(min = 2, max = 30) @NotBlank String> tagNames;
}
