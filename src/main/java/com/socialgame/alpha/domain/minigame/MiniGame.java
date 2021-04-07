package com.socialgame.alpha.domain.minigame;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class MiniGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String question;

    public MiniGame() {
    }

    public MiniGame(String question) {
        this.question = question;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
}
