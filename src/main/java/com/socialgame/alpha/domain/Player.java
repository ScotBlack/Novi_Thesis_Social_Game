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
    @Column
    private int points;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    public Player() {
    }

    public Player(String name, Color color, Boolean phone, Game game) {
        this.name = name;
        this.color = color;
        this.phone = phone;
        this.points = 0;
        this.game = game;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public Boolean getPhone() { return phone; }
    public void setPhone(Boolean phone) { this.phone = phone; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

}
