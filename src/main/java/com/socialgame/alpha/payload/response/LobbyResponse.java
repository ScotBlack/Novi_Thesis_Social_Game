package com.socialgame.alpha.payload.response;

public class LobbyResponse {
    private Long id;
    private Long gameId;
    private Boolean canStart;
    private String status;
    private String gameType;
    private int points;

    public LobbyResponse(Long id, Long gameId, Boolean canStart, String status, String gameType, int points) {
        this.id = id;
        this.gameId = gameId;
        this.canStart = canStart;
        this.status = status;
        this.gameType = gameType;
        this.points = points;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Boolean getCanStart() { return canStart; }
    public void setCanStart(Boolean canStart) { this.canStart = canStart; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}