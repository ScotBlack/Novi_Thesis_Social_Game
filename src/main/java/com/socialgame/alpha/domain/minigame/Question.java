package com.socialgame.alpha.domain.minigame;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Question extends MiniGame {

    private String answer;
    private Set<String> answers;
    private String topic;

    public Question() {
    }

    public Question(String question, String answer, Set<String> answers, String topic) {
        super(question);
        this.answer = answer;
        this.answers = answers;
        this.topic = topic;
    }



}
