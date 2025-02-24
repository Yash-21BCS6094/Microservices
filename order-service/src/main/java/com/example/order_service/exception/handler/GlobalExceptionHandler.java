package com.example.order_service.exception.handler;

import com.example.order_service.exception.JwtException;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.exception.ProductNotFoundException;
import com.example.order_service.exception.UserNotFoundException;
import org.hibernate.query.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<CustomError> handleOrderNotFoundException(final OrderNotFoundException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                CustomError.Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false,
                null
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<CustomError> handleProductNotFoundException(final ProductNotFoundException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                CustomError.Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false,
                null
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<CustomError> handleUserNotFoundException(final UserNotFoundException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                CustomError.Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false,
                null
        );

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<CustomError> handleJwtException(final JwtException ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAUTHORIZED,
                CustomError.Header.VALIDATION_ERROR.getName(),
                ex.getMessage(),
                false,
                null
        );

        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }

}
