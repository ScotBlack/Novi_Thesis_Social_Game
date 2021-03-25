package com.socialgame.alpha.payload.request;


import javax.validation.constraints.*;

public class NewPlayerRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank
//    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String phone;

    @NotNull
    private Long gameId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
}
