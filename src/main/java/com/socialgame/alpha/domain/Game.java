package com.socialgame.alpha.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Column
    private Boolean canStart;

    @OneToMany (mappedBy="game")
    private Set<Player> players;

    @OneToMany (mappedBy="game")
    private Set<Team> teams;

    public Game() {
    }

    public Game(String gameType) {
        this.gameType = gameType;
        this.points = 100;
        this.canStart = false;
        this.players = new HashSet<Player>();
        this.teams = new HashSet<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public Boolean getCanStart() { return canStart; }
    public void setCanStart(Boolean canStart) { this.canStart = canStart; }

    public Set<Player> getPlayers() { return players; }
    public void setPlayers(Set<Player> players) { this.players = players; }

    public Set<Team> getTeams() { return teams; }
    public void setTeams(Set<Team> teams) { this.teams = teams; }

    public void addPlayer (Player player) {
        this.players.add(player);
    }
}

