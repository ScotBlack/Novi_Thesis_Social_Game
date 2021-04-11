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

//    @OneToMany (mappedBy="game")
//    private Set<Player> players;
////
//    @Column
//    @ElementCollection
//    private Set<Color> teams;
//
//    @Column
//    @ElementCollection
//    private Set<Long> captains;
//
//    @Column
//    @ElementCollection
//    private List<Integer> scores;

    @ManyToOne
    private MiniGame currentMiniGame;

    public Game() {
    }

    public Game(GameType gameType) {
        this.gameType = gameType;
        this.points = 100;
        this.started = false;
        this.teams = new HashSet<>();

//        this.players = new HashSet<>();
//        this.captains = new HashSet<>();
//        this.scores = new ArrayList<>();
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

}

