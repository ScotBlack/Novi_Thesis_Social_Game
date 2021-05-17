package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.prototype.GamePrototype;
import com.socialgame.alpha.prototype.QuestionPrototype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId(1L);
        game.setGameIdString("abc");
        game.setLobby(new Lobby());
        game.setGameType(GameType.FFA);
        game.setScoreToWin(50);
        game.setStarted(false);
        game.setTeams(new HashSet<>());
        game.setCurrentMiniGame(QuestionPrototype.protoQuestion());
        game.setCurrentCompetingTeams(new HashSet<>());
    }

    @Test
    void propertiesTest() {
        assertAll("Properties Test",
            () -> assertAll("Game Properties",
                () ->  assertEquals(1L, game.getId()),
                () ->  assertEquals("abc", game.getGameIdString()),
                () ->  assertTrue(game.getLobby() instanceof Lobby),
                () ->  assertEquals(GameType.FFA, game.getGameType()),
                () ->  assertEquals(50, game.getScoreToWin()),
                () ->  assertEquals(false, game.getStarted()),
                () ->  assertTrue(game.getTeams() instanceof HashSet),
                () ->  assertTrue(game.getCurrentMiniGame() instanceof Question),
                () ->  assertTrue(game.getCurrentCompetingTeams() instanceof HashSet)
            )
        );
    }
}