package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.prototype.GamePrototype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GameTest {


    @BeforeEach
    void setUp() {
//        Game game = new Game();
//        game.setId(1L);
//        game.setGameIdString("abc");
//        game.setGameType(GameType.FFA);
//        game.setPoints(100);
//        game.setStarted(false);
    }

    @Test
    void getId() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        Long gameId = GamePrototype.protoGame().getId();

        assertEquals(1L, gameId );
    }

    @Test
    void getGameIdString() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        String gameIdString = GamePrototype.protoGame().getGameIdString();

        assertEquals("abc", gameIdString);
    }

    @Test
    void setGameIdString() {
    }

    @Test
    void getGameType() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        GameType gameType = GamePrototype.protoGame().getGameType();

        assertEquals(GameType.FFA, gameType);
    }

    @Test
    void setGameType() {
    }

    @Test
    void getPoints() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        int points = GamePrototype.protoGame().getPoints();

        assertEquals(100, points);
    }

    @Test
    void setPoints() {
    }

    @Test
    void getStarted() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        Boolean started = GamePrototype.protoGame().getStarted();

        assertEquals(false, started);
    }

    @Test
    void setStarted() {
    }

    @Test
    void getTeams() {
    }

    @Test
    void setTeams() {
    }

    @Test
    void getCurrentMiniGame() {
    }

    @Test
    void setCurrentMiniGame() {
    }

    @Test
    void getCurrentCompetingTeams() {
    }

    @Test
    void setCurrentCompetingTeams() {
    }
}