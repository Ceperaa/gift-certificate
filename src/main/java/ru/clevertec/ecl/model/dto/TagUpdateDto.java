package ru.clevertec.ecl.model.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagUpdateDto {

    @Size(min = 2, max = 30)
    String name;
}
