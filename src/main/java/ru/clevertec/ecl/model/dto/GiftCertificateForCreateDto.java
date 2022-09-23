package ru.clevertec.ecl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftCertificateForCreateDto {

    @NotEmpty
    @Size(min = 2,max = 30)
    private String name;
    @NotEmpty
    @Size(min = 2,max = 100)
    private String description;
    @Positive
    @DecimalMin("00.01")
    @DecimalMax("100.00")
    private BigDecimal price;
    @Min(1)
    @Max(30)
    private Integer duration;
    @Size(max = 30)
    private List<String> tagNames;
}
