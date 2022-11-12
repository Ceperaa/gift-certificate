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
public class GiftCertificateCreateDto {

    Long id;

    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @NotBlank
    @NotEmpty()
    @Size(min = 2, max = 100)
    String description;

    @Positive
    @DecimalMin("00.01")
    @DecimalMax("100.00")
    @NotNull
    BigDecimal price;

    @Min(1)
    @Max(30)
    @NotNull
    Integer duration;

    @Size(max = 30)
    List<@Size(min = 2, max = 30) @NotBlank String> tagNames;
}
