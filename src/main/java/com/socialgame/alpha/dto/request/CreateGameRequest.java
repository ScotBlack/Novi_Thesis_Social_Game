package com.socialgame.alpha.dto.request;

import javax.validation.constraints.*;

public class CreateGameRequest {

    @NotBlank(message = "Name is required")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
