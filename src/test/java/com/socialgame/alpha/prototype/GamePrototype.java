package com.socialgame.alpha.prototype;

import com.socialgame.alpha.domain.Game;

public class GamePrototype {

    public static Game aGame() {
        Game game = new Game();
        game.setGameIdString("abc");
        return game;
    }
}
