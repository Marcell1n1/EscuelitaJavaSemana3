package com.marcell.producto_ms.exceptions;

import java.util.Map;

public class FieldValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public FieldValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
    public Map<String, String> getErrors() { return errors; }
}
