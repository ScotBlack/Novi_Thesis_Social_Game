package com.socialgame.alpha.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @OneToMany
    private Set<Player> players;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;


    public Team() {
    }

    public Team(String name, Set<Player> players, Game game) {
        this.name = name;
        this.players = players;
        this.game = game;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Player> getPlayers() { return players; }
    public void setPlayers(Set<Player> players) { this.players = players; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
}
