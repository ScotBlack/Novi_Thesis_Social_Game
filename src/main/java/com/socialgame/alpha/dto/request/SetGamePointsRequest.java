package com.socialgame.alpha.dto.request;

import com.socialgame.alpha.domain.enums.GameType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SetGamePointsRequest {

    @NotBlank
    private String gameIdString;

    private String setting;

    private int gamePoints;

    private GameType gameType;

    public String getGameIdString() {
        return gameIdString;
    }
    public void setGameIdString(String gameIdString) {
        this.gameIdString = gameIdString;
    }

    public int getGamePoints() {
        return gamePoints;
    }
    public void setGamePoints(int points) {
        this.gamePoints = points;
    }
}
