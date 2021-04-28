package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.prototype.GamePrototype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GameTest {

    public GameTest() {
        Game game = new Game ("abc");
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
    void getGameType() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        GameType gameType = GamePrototype.protoGame().getGameType();

        assertEquals(GameType.FFA, gameType);
    }


    @Test
    void getPoints() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        int points = GamePrototype.protoGame().getPoints();

        assertEquals(100, points);
    }


    @Test
    void getStarted() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        Boolean started = GamePrototype.protoGame().getStarted();

        assertEquals(false, started);
    }

    @Test
    void getTeams() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        Set<Team> teams = GamePrototype.protoGame().getTeams();

        assertTrue(teams instanceof HashSet);
    }

    @Test
    void getCurrentMiniGame() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        MiniGame question = GamePrototype.protoGame().getCurrentMiniGame();

        assertTrue(question instanceof Question);
    }

    @Test
    void getCurrentCompetingTeams() {
        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Game.class,1L)).thenReturn(GamePrototype.protoGame());

        Set<Team> teams = GamePrototype.protoGame().getCurrentCompetingTeams();

        assertTrue(teams instanceof HashSet);
    }
}