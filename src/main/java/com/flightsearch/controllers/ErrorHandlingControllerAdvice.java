package com.flightsearch.controllers;


import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.exceptions.PermissionDeniedException;
import com.flightsearch.exceptions.schemas.ObjectNotFoundSchema;
import com.flightsearch.exceptions.schemas.PermissionDeniedSchema;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class,
            jakarta.validation.ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public Map<String, String> handleUniqueExceptions(
            DataIntegrityViolationException ex) {
        String constraintName = "Неизвестная ошибка DataIntegrityViolationException";
        if ((ex.getCause() != null) && (ex.getCause() instanceof ConstraintViolationException)) {
            constraintName = ex.getCause().getCause().getMessage();
        }
        return Map.of("error", constraintName);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ObjectNotFoundSchema handleNotFoundExceptions(NotFoundException exception) {
        return new ObjectNotFoundSchema(exception);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = PermissionDeniedException.class)
    public PermissionDeniedSchema handlePermissionDenied(PermissionDeniedException exception) {
        return new PermissionDeniedSchema(exception);
    }
}
