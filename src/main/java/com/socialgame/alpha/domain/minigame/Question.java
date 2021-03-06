package com.socialgame.alpha.domain.minigame;


import com.socialgame.alpha.domain.enums.MiniGameType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Question extends MiniGame {

    @Column
    private String correctAnswer;

    @ElementCollection
    private Set<String> allAnswers;


    public Question() {
    }

    public Question(MiniGameType miniGameType, String question, String correctAnswer, String[] wrongAnswers) {
        super(miniGameType, question, 10);
        this.correctAnswer = correctAnswer;
        this.allAnswers = new HashSet<>();
        this.allAnswers.add(correctAnswer);
        this.allAnswers.add(wrongAnswers[0]);
        this.allAnswers.add(wrongAnswers[1]);
        this.allAnswers.add(wrongAnswers[2]);

    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Set<String> getAllAnswers() {
        return allAnswers;
    }
    public void setAllAnswers(Set<String> wrongAnswers) {
        this.allAnswers = allAnswers;
    }
}
