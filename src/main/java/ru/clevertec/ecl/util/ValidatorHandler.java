package ru.clevertec.ecl.util;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
public class ValidatorHandler {

    @SneakyThrows
    public static String message(Errors bindingResult) {
        String result = "";
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                result = result
                        .concat(fieldError.getField())
                        .concat(" - ")
                        .concat(fieldError.getDefaultMessage())
                        .concat("; ");
            }
        return result;
    }
}



