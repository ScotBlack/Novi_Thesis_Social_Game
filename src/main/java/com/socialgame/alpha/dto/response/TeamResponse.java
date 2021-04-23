package com.socialgame.alpha.dto.response;

import java.util.Set;

public class TeamResponse {

    private Long teamId;
    private Long gameId;
    private String teamName;
    private Set<String> members;
    private int points;

    public TeamResponse(Long teamId, Long gameId, String teamName, Set<String> members, int points) {
        this.teamId = teamId;
        this.gameId = gameId;
        this.teamName = teamName;
        this.members = members;
        this.points = points;
    }

    public Long getTeamId() { return teamId;}
    public void setTeamId(Long teamId) { this.teamId = teamId;}

    public Long getGameId() { return gameId;}
    public void setGameId(Long gameId) { this.gameId = gameId;}

    public String getTeamName() { return teamName;}
    public void setTeamName(String teamName) { this.teamName = teamName;}

    public Set<String> getMembers() { return members;}
    public void setMembers(Set<String> members) { this.members = members;}

    public int getPoints() { return points;}
    public void setPoints(int points) { this.points = points;}
}
