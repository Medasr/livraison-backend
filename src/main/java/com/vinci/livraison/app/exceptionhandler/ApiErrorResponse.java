package com.vinci.livraison.app.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiErrorResponse {

    private Instant timestamp;
    private String error;
    private String message;
    private Map<String, Object> errors;

    public ApiErrorResponse(String error, String message, Map<String, Object> errors) {
        this.timestamp = Instant.now();
        this.error = error;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(String error, String message) {
        this(error, message, null);
    }
}
