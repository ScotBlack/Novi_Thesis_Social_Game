package com.socialgame.alpha.domain.minigame;

import com.socialgame.alpha.domain.enums.AgeSetting;
import com.socialgame.alpha.domain.enums.MiniGameType;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Question extends MiniGame {

    @Column
    private String correctAnswer;

    @ElementCollection
    private Set<String> allAnswers;

    @Column
    private String topic;

    public Question() {
    }

    public Question(MiniGameType miniGameType, String question, AgeSetting ageSetting, String correctAnswer, String[] wrongAnswers, String topic) {
        super(miniGameType, question, 10,ageSetting);
        this.correctAnswer = correctAnswer;
        this.allAnswers = new HashSet<>();
        this.allAnswers.add(correctAnswer);
        this.allAnswers.add(wrongAnswers[0]);
        this.allAnswers.add(wrongAnswers[1]);
        this.allAnswers.add(wrongAnswers[2]);
        this.topic = topic;
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

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
