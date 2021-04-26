package com.socialgame.alpha.dto.response;

public class LobbyResponse {
    private String gameIdString;
    private Boolean canStart;
    private String status;
    private String gameType;
    private int points;

    public LobbyResponse(String gameIdString, Boolean canStart, String status, String gameType, int points) {
        this.gameIdString = gameIdString;
        this.canStart = canStart;
        this.status = status;
        this.gameType = gameType;
        this.points = points;
    }

    public String getGameIdString() {
        return gameIdString;
    }

    public void setGameIdString(String gameIdString) {
        this.gameIdString = gameIdString;
    }

    public Boolean getCanStart() { return canStart; }
    public void setCanStart(Boolean canStart) { this.canStart = canStart; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
