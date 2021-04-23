package com.socialgame.alpha.dto.response;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private Map<String, String> errors;

    public ErrorResponse() { this.errors = new HashMap<>(); }

    public Map<String, String> getErrors() { return errors; }

    public void addError (String errorType, String errorMessage) {errors.put(errorType, errorMessage);}
}
