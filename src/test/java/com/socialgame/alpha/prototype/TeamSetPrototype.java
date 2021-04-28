package com.socialgame.alpha.prototype;

import com.socialgame.alpha.domain.Team;

import java.util.HashSet;
import java.util.Set;

public class TeamSetPrototype {
    public static Set<Team> protoTeamSet() {
        Set<Team> teamSet =new HashSet<>();
        teamSet.add(TeamPrototype.protoTeamOne());
        teamSet.add(TeamPrototype.protoTeamTwo());
        return teamSet;
    }
}
