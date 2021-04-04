package com.socialgame.alpha.payload.response;

public class VerifyPlayerResponse {
    private int gameId;
    private Boolean canStart;
    private String message;

    public VerifyPlayerResponse(int gameId, Boolean canStart, String message) {
        this.gameId = gameId;
        this.canStart = canStart;
        this.message = message;
    }

    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }

    public Boolean getCanStart() { return canStart; }
    public void setCanStart(Boolean canStart) { this.canStart = canStart; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
