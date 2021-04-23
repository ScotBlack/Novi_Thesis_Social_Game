package com.socialgame.alpha.dto.request;


import javax.validation.constraints.*;

public class JoinGameRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank
//    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String phone;

    @NotNull
    private Long lobbyId;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public Long getLobbyId() {return lobbyId;}
    public void setLobbyId(Long lobbyId) {this.lobbyId = lobbyId;}
}
