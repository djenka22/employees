package org.hyperoptic.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hyperoptic.infrastructure.exceptions.custom.BadRequestException;
import org.hyperoptic.model.Constraints;
import org.hyperoptic.infrastructure.exceptions.custom.DataNotFoundException;
import org.hyperoptic.infrastructure.exceptions.custom.LazyLoadException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final Constraints constraints;

    public ApiExceptionHandler(Constraints constraints) {
        this.constraints = constraints;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onConstraintViolationException(ConstraintViolationException ex){
        Set<Violation> violations = new HashSet<>();
        for(ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()){
            Violation violation = new Violation(
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getMessage(),
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            violations.add(violation);
        }
        return new ErrorResponse(violations);
    }
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Set<Violation> violations = new HashSet<>();

        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            Violation violation = new Violation(
                    fieldError.getField(),
                    fieldError.getDefaultMessage(),
                    ZonedDateTime.now(ZoneId.of("Z"))
            );
            violations.add(violation);
        }
        return new ResponseEntity<>(new ErrorResponse(violations), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        CustomErrorMessage errorMessage = new CustomErrorMessage(
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity(errorMessage,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorMessage onNotFoundException(Exception ex){


        return new CustomErrorMessage(
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }
    @ExceptionHandler(value = {org.hibernate.exception.ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorMessage onHibernateConstraintValidation(org.hibernate.exception.ConstraintViolationException ex){


        return new CustomErrorMessage(
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorMessage onDataIntegrityViolationException(DataIntegrityViolationException ex){
        String errorMessage = ex.getMessage();
        StringBuilder responseMessage = new StringBuilder("Violated constraint: ");
        constraints.getConstraints().stream().filter(c -> errorMessage.contains(c)).forEach(responseMessage::append);

        return new CustomErrorMessage(
                responseMessage.toString(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }

    @ExceptionHandler(value = {LazyLoadException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorMessage onLazyLoadException(LazyLoadException ex){

        return new CustomErrorMessage(
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorMessage onBadRequestException(BadRequestException ex){

        return new CustomErrorMessage(
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorMessage onBadRequestException(Exception ex){

        return new CustomErrorMessage(
                ex.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }




    @AllArgsConstructor
    @Data
    static class ErrorResponse {
        Set<Violation> violations;
    }
    @AllArgsConstructor
    @Data
    static class Violation {
        String field;
        String message;
        ZonedDateTime timestamp;
    }

    @AllArgsConstructor
    @Data
    static class CustomErrorMessage {
        String message;
        ZonedDateTime timestamp;
    }
}
