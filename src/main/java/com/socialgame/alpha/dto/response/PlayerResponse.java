package com.socialgame.alpha.dto.response;

public class PlayerResponse {

    private String username;
    private Long id;
    private String name;
    private String color;
    private Boolean phone;


    public PlayerResponse(String username, Long id, String name, String color, Boolean phone) {
        this.username = username;
        this.id = id;
        this.name = name;
        this.color = color;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Boolean getPhone() { return phone; }
    public void setPhone(Boolean phone) { this.phone = phone; }
}

