package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "lobby")
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

    @OneToMany (mappedBy = "lobby")
    private Set<Player> players;

    @Column
    @Value("Need more players.")
    private String status;

    @Column
    @Value("false")
    private Boolean canStart;

    public Lobby() {
    }

    public Lobby(GameType gameType) {
        this.gameType = GameType.FFA;
        this.points = 100;
        this.players = new HashSet<>();
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

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
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
