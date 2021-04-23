package com.socialgame.alpha.dto.response.minigame;

public class TeamAnswerResponse {

    private Long teamId;
    private String question;
    private String answer;
    private Boolean answerCorrect;
    private int pointsMiniGame;
    private int totalPoints;

    public TeamAnswerResponse(Long teamId, String question, String answer, Boolean answerCorrect, int pointsMiniGame, int totalPoints) {
        this.teamId = teamId;
        this.question = question;
        this.answer = answer;
        this.answerCorrect = answerCorrect;
        this.pointsMiniGame = pointsMiniGame;
        this.totalPoints = totalPoints;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(Boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public int getPointsMiniGame() {
        return pointsMiniGame;
    }

    public void setPointsMiniGame(int pointsMiniGame) {
        this.pointsMiniGame = pointsMiniGame;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
