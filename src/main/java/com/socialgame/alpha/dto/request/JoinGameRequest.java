package com.socialgame.alpha.dto.request;


import javax.validation.constraints.*;

public class JoinGameRequest {

    @NotBlank(message = "Name is required")
    private String username;

    @NotBlank
    private String gameIdString;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getGameIdString() {return gameIdString;}
    public void setGameIdString(String gameIdString) {this.gameIdString = gameIdString;}
}
