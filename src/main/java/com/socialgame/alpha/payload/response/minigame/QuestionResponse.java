package com.socialgame.alpha.payload.response.minigame;

import com.socialgame.alpha.domain.enums.MiniGameType;

import java.util.Set;

public class QuestionResponse {

    private Long game_id;
    private MiniGameType miniGameType;
    private Long question_id;
    private Set<Long> competingPlayers_id;
    private String question;
    private String[] answers;

    public QuestionResponse(Long game_id, MiniGameType miniGameType, Long question_id, Set<Long> competingPlayers_id, String question, String[] answers) {
        this.game_id = game_id;
        this.miniGameType = miniGameType;
        this.question_id = question_id;
        this.competingPlayers_id = competingPlayers_id;
        this.question = question;
        this.answers = answers;
    }

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }

    public MiniGameType getMiniGameType() {
        return miniGameType;
    }

    public void setMiniGameType(MiniGameType miniGameType) {
        this.miniGameType = miniGameType;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public Set<Long> getCompetingPlayers_id() {
        return competingPlayers_id;
    }

    public void setCompetingPlayers_id(Set<Long> competingPlayers_id) {
        this.competingPlayers_id = competingPlayers_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
}
