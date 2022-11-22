package ru.clevertec.ecl.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.xml.bind.ValidationException;
import java.util.List;

@Component
public class ValidatorHandler {

    public void message(Errors bindingResult) throws ValidationException {
        String result = "";
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                result = result
                        .concat(fieldError.getField())
                        .concat(" - ")
                        .concat(fieldError.getDefaultMessage())
                        .concat("; ");
            }
            throw new ValidationException(result);
        }
    }
}



