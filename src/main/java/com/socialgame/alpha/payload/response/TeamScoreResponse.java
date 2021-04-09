package com.socialgame.alpha.payload.response;

import java.util.HashSet;
import java.util.Set;

public class TeamScoreResponse {

    private String team;
    private int points;
    private Set<String> members;

    public TeamScoreResponse(String team) {
        this.team = team;
        this.members = new HashSet<>();
    }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public Set<String> getMembers() { return members; }
    public void setMembers(Set<String> members) { this.members = members; }
    public void addMembers(String member) { this.members.add(member); }
}
