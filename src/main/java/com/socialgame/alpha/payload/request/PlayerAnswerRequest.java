package com.socialgame.alpha.payload.request;

import com.socialgame.alpha.domain.enums.MiniGameType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PlayerAnswerRequest {

    @NotNull
    private Long gameId;

    @NotNull
    private Long playerId;

    private MiniGameType miniGameType;

    @NotBlank(message = "Answer is required")
    private String answer;


    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long player_id) { this.playerId = player_id; }

    public MiniGameType getMiniGameType() { return miniGameType; }
    public void setMiniGameType(MiniGameType miniGameType) { this.miniGameType = miniGameType; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }


}
