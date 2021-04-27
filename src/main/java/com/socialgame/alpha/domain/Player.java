package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    @Column
    private Color color;
    @Column
    private Boolean phone;

    @ManyToOne
    @JoinColumn(name="lobby_id")
    private Lobby lobby;

    public Player() {
    }

    public Player(String name, Color color, Boolean phone) {
        this.name = name;
        this.color = color;
        this.phone = phone;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name; }
    public void setName(String name) {this.name = name;}

    public Color getColor() {return color; }
    public void setColor(Color color) {this.color = color;}

    public Boolean getPhone() {return phone; }
    public void setPhone(Boolean phone) {this.phone = phone;}

    public Lobby getLobby() {return lobby;}
    public void setLobby(Lobby lobby) {this.lobby = lobby;}
}
