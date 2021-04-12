package com.socialgame.alpha.payload.response;

public class PlayerObjectResponse {

    private Long id;
    private String name;
    private String color;
    private Boolean phone;
    private Long lobbyId;

    public PlayerObjectResponse(Long id, String name, String color, Boolean phone, Long lobbyId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.phone = phone;
        this.lobbyId = lobbyId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Boolean getPhone() { return phone; }
    public void setPhone(Boolean phone) { this.phone = phone; }

    public Long getLobbyId() {return lobbyId;}
    public void setLobbyId(Long lobbyId) {this.lobbyId = lobbyId;}
}

