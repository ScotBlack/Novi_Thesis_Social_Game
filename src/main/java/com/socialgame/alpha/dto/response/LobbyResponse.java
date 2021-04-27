package com.socialgame.alpha.dto.response;

import com.socialgame.alpha.domain.Player;

import java.util.HashSet;
import java.util.Set;

public class LobbyResponse {
    private String gameIdString;
    private Boolean canStart;
    private String status;
    private String gameType;
    private int points;
    private Set<PlayerResponse> players;

    public LobbyResponse(String gameIdString, Boolean canStart, String status, String gameType, int points, Set<PlayerResponse> players) {
        this.gameIdString = gameIdString;
        this.canStart = canStart;
        this.status = status;
        this.gameType = gameType;
        this.points = points;
        this.players = new HashSet<>();
        this.players = players;
    }

    public String getGameIdString() {return gameIdString;}
    public void setGameIdString(String gameIdString) {this.gameIdString = gameIdString;}

    public Boolean getCanStart() { return canStart; }
    public void setCanStart(Boolean canStart) { this.canStart = canStart; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public int getPoints() { return points; }
    public void setPoints(int points) {this.points = points;}

    public Set<PlayerResponse> getPlayers() {return players;}
    public void setPlayers(Set<PlayerResponse> players) {this.players = players;}
}
