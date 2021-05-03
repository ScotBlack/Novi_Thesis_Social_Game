package com.socialgame.alpha.dto.request;

import com.socialgame.alpha.domain.enums.GameType;

public class SetGameTypeRequest {
    private Long gameId;
    private GameType gameType;

    public Long getGameId() {
        return gameId;
    }
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public GameType getGameType() {
        return gameType;
    }
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
