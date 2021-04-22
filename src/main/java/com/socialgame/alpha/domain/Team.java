package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Game game;

    private Color name;

    @ElementCollection
    private Map<String, Integer> players;

    private int points;

    public Team() {
    }

    public Team(Game game, Color name) {
        this.game = game;
        this.name = name;
        this.players = new HashMap<>();
        this.points = 0;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    public Color getName() {
        return name;
    }
    public void setName(Color name) {
        this.name = name;
    }

    public Map<String, Integer> getPlayers() {
        return players;
    }
    public void setPlayers(Map<String, Integer> players) {
        this.players = players;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
