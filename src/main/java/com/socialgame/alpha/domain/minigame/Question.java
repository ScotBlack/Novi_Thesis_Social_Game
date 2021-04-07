package com.socialgame.alpha.domain.minigame;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Question extends MiniGame {

    @Column
    private String answer;

    @ElementCollection
    private Set<String> answers;

    @Column
    private String topic;

    public Question() {
    }

    public Question(String question, String answer, Set<String> answers, String topic) {
        super(question);
        this.answer = answer;
        this.answers = new HashSet<>();
        this.answers = answers;
        this.topic = topic;
    }
}
