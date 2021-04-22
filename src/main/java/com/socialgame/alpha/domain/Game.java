package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.domain.minigame.MiniGame;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private GameType gameType;
    @Column
    private int points;
    @Column
    private Boolean started;

    @OneToMany (mappedBy = "game")
    private Set<Team> teams;

    @ManyToOne
    private MiniGame currentMiniGame;

    @OneToMany
    private Set<Team> currentCompetingTeams;

    // teams that have answered or sth (so cant get mutliple points per question)
    // also it can check if all teams have answered, to continue

    public Game() {
    }

    public Game(GameType gameType) {
        this.gameType = gameType;
        this.points = 100;
        this.started = false;
        this.teams = new HashSet<>();
        this.currentCompetingTeams = new HashSet<>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public GameType getGameType() {
        return gameType;
    }
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public Boolean getStarted() {
        return started;
    }
    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Set<Team> getTeams() {
        return teams;
    }
    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public MiniGame getCurrentMiniGame() {
        return currentMiniGame;
    }
    public void setCurrentMiniGame(MiniGame currentMiniGame) {
        this.currentMiniGame = currentMiniGame;
    }

    public Set<Team> getCurrentCompetingTeams() {
        return currentCompetingTeams;
    }
    public void setCurrentCompetingTeams(Set<Team> currentCompetingTeams) {
        this.currentCompetingTeams = currentCompetingTeams;
    }
}

