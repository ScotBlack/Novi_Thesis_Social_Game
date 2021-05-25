package com.socialgame.alpha.domain.minigame;


import com.socialgame.alpha.domain.enums.MiniGameType;

import javax.persistence.*;

@Entity
//@Inheritance
public abstract class MiniGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private MiniGameType miniGameType;

    @Column
    private String question;

    @Column
    private int points;

    public MiniGame() {
    }

    public MiniGame(MiniGameType miniGameType, String question, int points) {
        this.miniGameType = miniGameType;
        this.question = question;
        this.points = points;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public MiniGameType getMiniGameType() { return miniGameType; }
    public void setMiniGameType(MiniGameType miniGameType) { this.miniGameType = miniGameType; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

}
