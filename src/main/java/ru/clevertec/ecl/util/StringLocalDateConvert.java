package ru.clevertec.ecl.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringLocalDateConvert extends StdConverter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS"));
    }
}
