package com.socialgame.alpha.domain.minigame;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.enums.AgeSetting;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance
public abstract class MiniGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String question;

    @Column
    @ElementCollection
    private Set<Long> competingPlayers;

    @Column
    private int points;

    @Column
    private AgeSetting ageSetting;

    public MiniGame() {
    }

    public MiniGame(String question, int points, AgeSetting ageSetting) {
        this.question = question;
        this.competingPlayers = new HashSet<>();
        this.points = points;
        this.ageSetting = ageSetting;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Set<Long> getCompetingPlayers() { return competingPlayers; }
    public void setCompetingPlayers(Set<Long> competingPlayers) { this.competingPlayers = competingPlayers; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public AgeSetting getAgeSetting() { return ageSetting; }
    public void setAgeSetting(AgeSetting ageSetting) { this.ageSetting = ageSetting; }
}
