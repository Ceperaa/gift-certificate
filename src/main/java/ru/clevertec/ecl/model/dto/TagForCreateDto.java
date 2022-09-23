package ru.clevertec.ecl.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagForCreateDto {

    @NotEmpty
    @Size(min = 2,max = 30)
    private String name;
}
