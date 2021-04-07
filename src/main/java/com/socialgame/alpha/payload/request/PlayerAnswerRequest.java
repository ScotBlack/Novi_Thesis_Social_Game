package com.socialgame.alpha.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PlayerAnswerRequest {

    @NotBlank(message = "Answer is required")
    private String answer;

    @NotNull
    private Long gameId;

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
}
