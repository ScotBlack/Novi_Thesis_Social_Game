package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.AgeSetting;
import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {

    Question question;
    String[] answers= {"D", "E", "F"};

    @BeforeEach
    void setUp() {
        question = new Question(MiniGameType.BEST_ANSWER, "How?", AgeSetting.NORMAL, "B", answers, "Wrong");

        question.setId(1L);
        question.setMiniGameType(MiniGameType.QUESTION);
        question.setQuestion("Why?");
        question.setPoints(10);
        question.setAgeSetting(AgeSetting.FAMILY);
        question.setCorrectAnswer("A");
        question.setAllAnswers(new HashSet<>());
        question.setTopic("Test");
    }

    @Test
    void questionPropertiesTest() {
        assertAll("Properties Test",
                () -> assertAll("Team Properties",
                        () ->  assertEquals(1L, question.getId()),
                        () ->  assertEquals(MiniGameType.QUESTION, question.getMiniGameType()),
                        () ->  assertEquals("Why?",  question.getQuestion()),
                        () ->  assertEquals(10,  question.getPoints()),
                        () ->  assertEquals(AgeSetting.FAMILY,  question.getAgeSetting()),
                        () ->  assertEquals("A",  question.getCorrectAnswer()),
//                        () ->  assertTrue(question.getAllAnswers() instanceof String[]),
                        () ->  assertEquals("Test",  question.getTopic())
                )
        );

    }
}
