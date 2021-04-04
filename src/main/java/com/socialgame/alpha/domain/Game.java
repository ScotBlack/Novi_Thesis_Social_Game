package com.socialgame.alpha.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String gameType;

    @Column
    private int points;

    @OneToMany (mappedBy="game")
    private Set<Player> players;

    public Game() {
    }

    public Game(String gameType) {
        this.gameType = gameType;
        this.points = 100;
        this.players = new HashSet<Player>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public Set<Player> getPlayers() { return players; }
    public void setPlayers(Set<Player> players) { this.players = players; }

    public void addPlayer (Player player) {
        this.players.add(player);
    }
}

