package com.example.api_gateway.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

public class CustomError {

    private final HttpStatus httpStatus;
    private final String header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    private final Boolean isSuccess;

    // Constructor
    public CustomError(HttpStatus httpStatus, String header, String message, Boolean isSuccess) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.message = message;
        this.isSuccess = (isSuccess != null) ? isSuccess : false;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }
}

