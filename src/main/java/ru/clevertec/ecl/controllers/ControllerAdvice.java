package ru.clevertec.ecl.controllers;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.ecl.exception.ObjectNotFoundException;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Exceptions Handler
 *
 * @author Sergey Degtyarev
 */

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {
            JsonParseException.class,
            JsonMappingException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionObject response400(@RequestBody Exception e) {
        return aggregate(e.getMessage(), HttpStatus.BAD_REQUEST, e.getClass().getName());
    }

    @ExceptionHandler(value = {
            ValidationException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response422(@RequestBody Exception e) {
        return aggregate(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e.getClass().getName());
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionObject response404(@RequestBody Exception e) {
        return aggregate(e.getMessage(), HttpStatus.NOT_FOUND, e.getClass().getName());
    }

    private ExceptionObject aggregate(String message, HttpStatus status, String exceptionName) {
        return ExceptionObject
                .builder()
                .code(String.valueOf(status.value()))
                .status(String.valueOf(status))
                .exceptionName(exceptionName)
                .message(message)
                .build();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ExceptionObject {

    private String code;
    private String status;
    private String exceptionName;
    private String message;
}