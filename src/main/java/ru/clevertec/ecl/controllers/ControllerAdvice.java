package ru.clevertec.ecl.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.exception.NodeNotActiveException;
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
    public ExceptionObject response400( Exception e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return aggregate(e.getMessage(), badRequest, getCode(e, badRequest));
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response422( Exception e) {
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        return aggregate(e.getMessage(), unprocessableEntity, getCode(e, unprocessableEntity));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response42206( MethodArgumentNotValidException e) {
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        return aggregate(ValidatorHandler.message(e.getBindingResult()),
                unprocessableEntity, getCode(e, unprocessableEntity));
    }

    @ExceptionHandler(value = {EntityNotFoundException.class, NodeNotActiveException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionObject response404( Exception e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return aggregate(e.getMessage(), notFound, getCode(e, notFound));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> response( HttpClientErrorException e) {
        return new ResponseEntity<>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> response( HttpServerErrorException e) {
        return new ResponseEntity<>(e.getMessage(), e.getResponseHeaders(), e.getStatusCode());
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

    @Data
    @AllArgsConstructor
    @Builder
    private static class ExceptionObject {

        private String code;
        private String status;
        private String message;
    }
}