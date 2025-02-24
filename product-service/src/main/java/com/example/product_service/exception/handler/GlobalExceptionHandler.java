package com.example.product_service.exception.handler;

import com.example.product_service.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<CustomError> handlePasswordNotValidException(final ProductNotFoundException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                CustomError.Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false,
                null
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }
}