package com.socialgame.alpha.prototype;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.enums.Color;

import java.util.HashMap;


public class TeamPrototype {

    public static Team protoTeamOne() {
        Team teamOne = new Team();
            teamOne.setId(2L);
            teamOne.setGame(GamePrototype.protoGame());
            teamOne.setName(Color.RED);
////            teamOne.setPlayers(new HashMap<String,Integer>());
//            teamOne.getPlayers().put("PlayerOne", 10);
//            teamOne.getPlayers().put("PlayerTwo", 5);
//            teamOne.setPoints(50);
        return teamOne;
    }
    public static Team protoTeamTwo() {
        Team teamTwo = new Team();
            teamTwo.setId(3L);
            teamTwo.setGame(GamePrototype.protoGame());
            teamTwo.setName(Color.BLUE);
//            teamTwo.setPlayers(new HashMap<String,Integer>());
//            teamTwo.getPlayers().put("PlayerThree", 5);
//            teamTwo.getPlayers().put("PlayerFour", 10);
//            teamTwo.setPoints(25);
        return teamTwo;
    }
}
