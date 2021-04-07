package com.socialgame.alpha.domain;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long gameId;

    @Column
    private GameType gameType;
    @Column
    @Value("50")
    private int points;

    @Column
    @Value("Need more players.")
    private String status;
    @Column
    @Value("false")
    private Boolean canStart;

    public Lobby() {
    }

    public Lobby(Player player, Long gameId) {
        this.gameId = gameId;
        this.gameType = GameType.FFA;
        this.points = 100;
        this.status = "Need more players.";
        this.canStart = false;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }
    public void setGameId(Long gameId) {
        this.gameId = gameId;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCanStart() {
        return canStart;
    }
    public void setCanStart(Boolean canStart) {
        this.canStart = canStart;
    }
}
