package com.socialgame.alpha.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
