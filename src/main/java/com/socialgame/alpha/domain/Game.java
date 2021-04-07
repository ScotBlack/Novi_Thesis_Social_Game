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
    private Boolean started;

    @OneToMany (mappedBy="game")
    private Set<Player> players;

    @Column
    @ElementCollection
    private Set<Color> teams;

    @Column
    @ElementCollection
    private Set<Long> captains;

    @Column
    @ElementCollection
    private List<Integer> scores;

    public Game() {
    }

    public Game(String gameType) {
        this.gameType = gameType;
        this.points = 100;
        this.started = false;
        this.players = new HashSet<Player>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Set<Player> getPlayers() { return players; }
    public void setPlayers(Set<Player> players) { this.players = players; }

    public Set<Color> getTeams() {
        return teams;
    }

    public void setTeams(Set<Color> teams) {
        this.teams = teams;
    }

    public Set<Long> getCaptains() {
        return captains;
    }

    public void setCaptains(Set<Long> captains) {
        this.captains = captains;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public void addPlayer (Player player) {
        this.players.add(player);
    }
}

