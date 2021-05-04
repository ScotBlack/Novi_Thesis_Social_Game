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
        game = new Game("abc");
        game.setId(1L);
        game.setGameIdString("abc");
        game.setLobby(new Lobby());
        game.setGameType(GameType.FFA);
        game.setPoints(100);
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
                        () ->  assertEquals(100, game.getPoints()),
                        () ->  assertEquals(false, game.getStarted()),
                        () ->  assertTrue(game.getTeams() instanceof HashSet),
                        () ->  assertTrue(game.getCurrentMiniGame() instanceof Question),
                        () ->  assertTrue(game.getCurrentCompetingTeams() instanceof HashSet)
            ));
    }

//    @Test
//    void getId() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        Long gameId = GamePrototype.protoGame().getId();
//
//        assertEquals(1L, gameId );
//    }
//
//    @Test
//    void getGameIdString() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        String gameIdString = GamePrototype.protoGame().getGameIdString();
//
//        assertEquals("abc", gameIdString);
//    }
//
//
//    @Test
//    void getGameType() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        GameType gameType = GamePrototype.protoGame().getGameType();
//
//        assertEquals(GameType.FFA, gameType);
//    }
//
//
//    @Test
//    void getPoints() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        int points = GamePrototype.protoGame().getPoints();
//
//        assertEquals(100, points);
//    }
//
//
//    @Test
//    void getStarted() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        Boolean started = GamePrototype.protoGame().getStarted();
//
//        assertEquals(false, started);
//    }
//
//    @Test
//    void getTeams() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        Set<Team> teams = GamePrototype.protoGame().getTeams();
//
//        assertTrue(teams instanceof HashSet);
//    }
//
//    @Test
//    void getCurrentMiniGame() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        MiniGame question = GamePrototype.protoGame().getCurrentMiniGame();
//
//        assertTrue(question instanceof Question);
//    }
//
//    @Test
//    void getCurrentCompetingTeams() {
//        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());
//
//        Set<Team> teams = GamePrototype.protoGame().getCurrentCompetingTeams();
//
//        assertTrue(teams instanceof HashSet);
//    }
}