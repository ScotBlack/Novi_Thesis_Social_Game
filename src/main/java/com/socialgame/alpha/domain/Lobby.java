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
    @Value("ffa")
    private String gameType;
    @Column
    @Value("50")
    private int points;

    @Column
    @Value("Need more players.")
    private String status;
    @Column
    @Value("false")
    private Boolean canStart;

    @OneToMany (mappedBy="game")
    private Set<Player> players;

    public Lobby() {
    }

    public Lobby(Player player, Long gameId) {
        this.gameId = gameId;
        this.gameType = "FFA";
        this.points = 100;
        this.status = "Need more players.";
        this.canStart = false;
        this.players = new HashSet<>();
        this.players.add(player);
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

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
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

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
