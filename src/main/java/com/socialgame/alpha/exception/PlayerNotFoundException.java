package com.socialgame.alpha.exception;

import java.util.HashMap;
import java.util.Map;

public class PlayerNotFoundException extends GlobalExceptionHandler {

    private static final int errorType= 404;
    private Map<Integer, String> errors;



    public PlayerNotFoundException(int i, String errorMessage) {
        this.errors = new HashMap<>();
        this.errors.put(errorType, errorMessage);
    }

//    public Map<String, String> getErrors() { return errors; }
//    public void addError (String errorType, String errorMessage) {errors.put(errorType, errorMessage);}
}
