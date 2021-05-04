package com.socialgame.alpha.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.service.validation.EnumValidator;

import javax.validation.constraints.NotNull;

public class SetGameTypeRequest {

    @NotNull
    private String gameIdString;

    @JsonProperty("gameType")
    @EnumValidator(
            enumClass = GameType.class,
            message = "This error is coming from the enum class",
            groups = {GameType.class}
    )
    private GameType gameType;

    public String getGameIdString() {
        return gameIdString;
    }
    public void setGameIdString(String gameIdString) {
        this.gameIdString = gameIdString;
    }

    public GameType getGameType() {
        return gameType;
    }
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }


}
