package com.socialgame.alpha.dto.request;

import javax.validation.constraints.*;

public class CreateGameRequest {

    @Size(min = 3 , max = 12, message = "Username length must be between 3 and 12 characters long.")
    private String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
