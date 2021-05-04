package com.socialgame.alpha.dto.request;

import javax.validation.constraints.*;

public class CreateGameRequest {

    @NotBlank(message = "Name is required")
    private String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
