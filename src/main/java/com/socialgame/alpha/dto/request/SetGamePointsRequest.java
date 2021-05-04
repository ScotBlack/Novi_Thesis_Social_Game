package com.socialgame.alpha.dto.request;

import javax.validation.constraints.NotNull;

public class SetGamePointsRequest {

    @NotNull
    private String gameIdString;

    @NotNull
    private int gamePoints;

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
