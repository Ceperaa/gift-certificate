package ru.clevertec.ecl.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.ecl.exception.EntityNotFoundException;

import javax.annotation.PostConstruct;
import javax.xml.bind.ValidationException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

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
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return aggregate(e.getMessage(), badRequest, code(e, badRequest));
    }

    @ExceptionHandler(value = {
            ValidationException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response422(@RequestBody Exception e) {
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        return aggregate(e.getMessage(), unprocessableEntity, code(e, unprocessableEntity));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionObject response404(@RequestBody Exception e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return aggregate(e.getMessage(), notFound, code(e, notFound));
    }

    private ExceptionObject aggregate(String message, HttpStatus status, String code) {
        return ExceptionObject
                .builder()
                .code(code)
                .status(String.valueOf(status))
                .message(message)
                .build();
    }

    @PostConstruct
    public Map<Class<?>, String> map() {
        Map<Class<?>, String> map = new HashMap<>();
        map.put(JsonParseException.class, "01");
        map.put(JsonMappingException.class, "02");
        map.put(ValidationException.class, "03");
        map.put(IllegalArgumentException.class, "04");
        map.put(EntityNotFoundException.class, "05");
        return map;
    }

    private String code(Exception e, HttpStatus status){
        return MessageFormat.format("{0}{1}", status.toString(), map().get(e.getClass().getName()));
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ExceptionObject {

    private String code;
    private String status;
    private String message;
}