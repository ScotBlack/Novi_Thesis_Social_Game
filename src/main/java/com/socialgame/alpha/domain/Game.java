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
    private String gameIdString;

    @OneToOne
    private Lobby lobby;

    @Column
    private GameType gameType;
    @Column
    private int scoreToWin;
    @Column
    private Boolean started;

    @OneToMany (mappedBy = "game")
    private Set<Team> teams;

    @ManyToOne
    private MiniGame currentMiniGame;

    @OneToMany
    private Set<Team> currentCompetingTeams;

    public Game() {
    }

    public Game(String gameIdString) {
        this.gameIdString = gameIdString;
        this.gameType = GameType.FFA;
        this.scoreToWin = 50;
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

    public String getGameIdString() { return gameIdString; }
    public void setGameIdString(String gameIdString) { this.gameIdString = gameIdString; }

    public Lobby getLobby() {
        return lobby;
    }
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public GameType getGameType() {
        return gameType;
    }
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getScoreToWin() {
        return scoreToWin;
    }
    public void setScoreToWin(int scoreToWin) {
        this.scoreToWin = scoreToWin;
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

