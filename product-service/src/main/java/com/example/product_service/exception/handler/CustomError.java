package com.example.product_service.exception.handler;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<CustomSubError> subErrors;

    // Constructor
    public CustomError(HttpStatus httpStatus, String header, String message, Boolean isSuccess, List<CustomSubError> subErrors) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.message = message;
        this.isSuccess = (isSuccess != null) ? isSuccess : false;
        this.subErrors = subErrors;
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

    public List<CustomSubError> getSubErrors() {
        return subErrors;
    }

    /**
     * Represents a sub-error with specific details.
     */
    @Getter
    public static class CustomSubError {

        private final String message;
        private final String field;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Object value;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String type;

        // Constructor
        public CustomSubError(String message, String field, Object value, String type) {
            this.message = message;
            this.field = field;
            this.value = value;
            this.type = type;
        }
    }

    /**
     * Enumerates common error headers for categorizing errors.
     */
    public enum Header {
        API_ERROR("API ERROR"),
        ALREADY_EXIST("ALREADY EXIST"),
        NOT_FOUND("NOT EXIST"),
        VALIDATION_ERROR("VALIDATION ERROR"),
        DATABASE_ERROR("DATABASE ERROR"),
        PROCESS_ERROR("PROCESS ERROR"),
        AUTH_ERROR("AUTH ERROR");

        private final String name;

        // Constructor for enum
        Header(String name) {
            this.name = name;
        }

        // Explicit getter method
        public String getName() {
            return name;
        }
    }
}