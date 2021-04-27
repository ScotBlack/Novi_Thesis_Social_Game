package com.socialgame.alpha.dto.response;

public class PlayerResponse {

    private Long id;
    private String name;
    private String color;
    private Boolean phone;


    public PlayerResponse(Long id, String name, String color, Boolean phone) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.phone = phone;
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

