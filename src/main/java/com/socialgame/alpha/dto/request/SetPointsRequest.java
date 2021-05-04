package com.socialgame.alpha.dto.request;

import javax.validation.constraints.NotNull;

public class SetPointsRequest {

    @NotNull
    private Long gameId;

    @NotNull
    private int points;

    public Long getGameId() {
        return gameId;
    }
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
