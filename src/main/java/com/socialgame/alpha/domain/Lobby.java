package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
//@Table(name = "lobby")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String gameIdString;

    @OneToOne
    private Game game;

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

    public Lobby(Player player, String gameIdString) {
        this.players = new HashSet<>();
        this.players.add(player);
        this.gameIdString = gameIdString;
        this.status = "Need more players.";
        this.canStart = false;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getGameIdString() {
        return gameIdString;
    }

    public void setGameIdString(String gameIdString) {
        this.gameIdString = gameIdString;
    }

    public Game getGame() {return game;}
    public void setGame(Game game) {this.game = game;}

    public Set<Player> getPlayers() {return players;}
    public void setPlayers(Set<Player> players) {this.players = players;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public Boolean getCanStart() {return canStart;}
    public void setCanStart(Boolean canStart) {this.canStart = canStart;}
}
