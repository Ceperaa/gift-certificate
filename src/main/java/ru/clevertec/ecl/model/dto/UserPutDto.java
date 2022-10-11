package ru.clevertec.ecl.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPutDto {

    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

    @NotEmpty
    @Size(min = 2, max = 30)
    String surname;
}
