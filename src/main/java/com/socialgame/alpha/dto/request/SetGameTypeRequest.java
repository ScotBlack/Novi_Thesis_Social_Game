package com.socialgame.alpha.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.service.validation.EnumValidator;

public class SetGameTypeRequest {
    private Long gameId;

    @JsonProperty("gameType")
    @EnumValidator(
            enumClass = GameType.class,
            message = "This error is coming from the enum class"
//            groups = {Group1.class}
    )
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
