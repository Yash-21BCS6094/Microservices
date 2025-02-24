package com.example.order_service.exception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
