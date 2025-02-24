package com.example.api_gateway.exception.handler;

import com.example.api_gateway.exception.InvalidAccessException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidAccessException.class)
    protected ResponseEntity<Object> handleAddressNotFoundException(final InvalidAccessException ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAUTHORIZED,
                Header.API_ERROR.getName(),
                ex.getMessage(),
                false
        );

        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }
}
