package com.socialgame.alpha.prototype;


import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.Question;

import java.util.HashSet;

public class QuestionPrototype {


    public static Question protoQuestion() {
        Question question= new Question();
            question.setId(4L);
            question.setMiniGameType(MiniGameType.QUESTION);
            question.setQuestion("What color is a banana?");
            question.setPoints(5);
            question.setCorrectAnswer("Yellow");
            question.setAllAnswers(new HashSet<>());
        return question;
    }
}
