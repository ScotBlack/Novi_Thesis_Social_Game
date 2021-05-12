package com.socialgame.alpha.dto.response;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private Map<String, String> errors;

    public ErrorResponse() { this.errors = new HashMap<>(); }

    public ErrorResponse(Map<String, String> errors) {
        this.errors = new HashMap<>();
        this.errors = errors;
    }

    public static ErrorResponse build(Exception exception, String cause) {
        Map<String, String> errors = new HashMap<>();
        errors.put(cause, exception.getMessage());

        return new ErrorResponse(errors);
    }

    public Map<String, String> getErrors() { return errors; }

    public void addError (String errorType, String errorMessage) {errors.put(errorType, errorMessage);}
}
