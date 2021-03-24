package com.socialgame.alpha.domain;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String color;
    private Boolean phone;

    @OneToOne
    private Game game;

    public Player() {
    }

    public Player(String name, String color, Boolean phone, Game game) {
        this.name = name;
        this.color = color;
        this.phone = phone;
        this.game = game;
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
