package com.socialgame.alpha.prototype;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.enums.GameType;

import java.util.HashSet;

public class GamePrototype {

    public static Game protoGame() {
        Game game = new Game();
        game.setId(1L);
        game.setGameIdString("abc");
        game.setGameType(GameType.FFA);
        game.setPoints(100);
        game.setStarted(false);
        game.setTeams(new HashSet<>());
        game.getTeams().add(TeamPrototype.protoTeamOne());
        game.getTeams().add(TeamPrototype.protoTeamTwo());
        return game;
    }
}
