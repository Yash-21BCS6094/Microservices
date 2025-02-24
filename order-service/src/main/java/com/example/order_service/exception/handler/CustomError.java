package com.example.order_service.exception.handler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomError {

    private int statusCode;
    private String statusText;
    private String header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private Boolean isSuccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CustomSubError> subErrors;

    @JsonCreator
    public CustomError(
            @JsonProperty("httpStatus") HttpStatus httpStatus,
            @JsonProperty("header") String header,
            @JsonProperty("message") String message,
            @JsonProperty("isSuccess") Boolean isSuccess,
            @JsonProperty("subErrors") List<CustomSubError> subErrors
    ) {
        this.statusCode = (httpStatus != null) ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.statusText = (httpStatus != null) ? httpStatus.getReasonPhrase() : "Internal Server Error";
        this.header = header;
        this.message = message;
        this.isSuccess = (isSuccess != null) ? isSuccess : false;
        this.subErrors = subErrors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
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

    public static class CustomSubError {

        private String message;
        private String field;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object value;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String type;

        @JsonCreator
        public CustomSubError(
                @JsonProperty("message") String message,
                @JsonProperty("field") String field,
                @JsonProperty("value") Object value,
                @JsonProperty("type") String type
        ) {
            this.message = message;
            this.field = field;
            this.value = value;
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    public enum Header {
        API_ERROR("API ERROR"),
        ALREADY_EXIST("ALREADY EXIST"),
        NOT_FOUND("NOT EXIST"),
        VALIDATION_ERROR("VALIDATION ERROR"),
        DATABASE_ERROR("DATABASE ERROR"),
        PROCESS_ERROR("PROCESS ERROR"),
        AUTH_ERROR("AUTH ERROR");

        private final String name;

        Header(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
