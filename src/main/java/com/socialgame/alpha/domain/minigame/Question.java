package com.socialgame.alpha.domain.minigame;

import com.socialgame.alpha.domain.enums.AgeSetting;
import com.socialgame.alpha.domain.enums.MiniGameType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Question extends MiniGame {

    @Column
    private String correctAnswer;

    @ElementCollection
    private Set<String> wrongAnswers;

    @Column
    private String topic;

    public Question() {
    }

    public Question(MiniGameType miniGameType, String question, AgeSetting ageSetting, String correctAnswer, Set<String> wrongAnswers, String topic) {
        super(miniGameType, question, 10,ageSetting);
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = new HashSet<>();
        this.wrongAnswers = wrongAnswers;
        this.topic = topic;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Set<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(Set<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
