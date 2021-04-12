package com.socialgame.alpha.payload.request;

import com.socialgame.alpha.domain.enums.MiniGameType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TeamAnswerRequest {

    @NotNull
    private Long gameId;

    @NotNull
    private Long teamId;

    private MiniGameType miniGameType;

    @NotBlank(message = "Answer is required")
    private String answer;


    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }

    public MiniGameType getMiniGameType() { return miniGameType; }
    public void setMiniGameType(MiniGameType miniGameType) { this.miniGameType = miniGameType; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }


}
