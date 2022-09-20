package ru.clevertec.ecl.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {

    private Integer limit;
    private Integer offset;
    private Sorting sorting;

    public static Page of(Integer limit, Integer offset, Sorting sorting) {
        return Page
                .builder()
                .limit(limit)
                .offset(offset)
                .sorting(sorting)
                .build();
    }

    public static Page of(Integer limit, Integer offset) {
        return Page
                .builder()
                .limit(limit)
                .offset(offset)
                .build();
    }
}
