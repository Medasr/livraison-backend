package com.vinci.livraison.app.module.shared.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {

    Map<String,Object> errors;

    public ValidationException(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }
}
