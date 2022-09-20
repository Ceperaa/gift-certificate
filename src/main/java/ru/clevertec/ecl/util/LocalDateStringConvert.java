package ru.clevertec.ecl.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateStringConvert extends StdConverter<LocalDateTime, String> {

    public static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime
                .format(DateTimeFormatter
                        .ofPattern(DATA_FORMAT));

    }
}
