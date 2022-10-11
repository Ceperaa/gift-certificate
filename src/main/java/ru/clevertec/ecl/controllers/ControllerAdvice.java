package ru.clevertec.ecl.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.util.ValidatorHandler;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.Map;

/**
 * Exceptions Handler
 *
 * @author Sergey Degtyarev
 */

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    private final Map<Class<?>, String> map;

    @ExceptionHandler(value = {
            JsonParseException.class,
            JsonMappingException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionObject response400(@RequestBody Exception e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return aggregate(e.getMessage(), badRequest, getCode(e, badRequest));
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response422(@RequestBody Exception e) {
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        return aggregate(e.getMessage(), unprocessableEntity, getCode(e, unprocessableEntity));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response42206(@RequestBody MethodArgumentNotValidException e) {
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        return aggregate(ValidatorHandler.message(e.getBindingResult()),
                unprocessableEntity, getCode(e, unprocessableEntity));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionObject response404(@RequestBody Exception e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return aggregate(e.getMessage(), notFound, getCode(e, notFound));
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
    private Map<Class<?>, String> codeMap() {
        map.put(JsonParseException.class, "01");
        map.put(JsonMappingException.class, "02");
        map.put(ValidationException.class, "03");
        map.put(IllegalArgumentException.class, "04");
        map.put(EntityNotFoundException.class, "05");
        map.put(MethodArgumentNotValidException.class, "06");
        map.put(ConstraintViolationException.class, "07");
        map.put(MethodArgumentTypeMismatchException.class, "08");
        return map;
    }

    private String getCode(Exception e, HttpStatus status) {
        return String.valueOf(status.value()).concat(map.get(e.getClass()));
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