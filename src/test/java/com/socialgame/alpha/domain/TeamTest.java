package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TeamTest {

    @Test
    void getId() {
        Team team = new Team();
        team.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Team.class,1L)).thenReturn(team);

        Long teamId = team.getId();

        assertEquals(1L, teamId);
    }

    @Test
    void getGame() {
        Team team = new Team();
        team.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Team.class,1L)).thenReturn(team);

        Long teamId = team.getId();

        assertEquals(1L, teamId);
    }

    @Test
    void getName() {
        Team team = new Team(new Game(), Color.RED);
        team.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Team.class,1L)).thenReturn(team);

        Game game = team.getGame();

        assertTrue(game instanceof Game);
    }

    @Test
    void getPlayers() {
    }

    @Test
    void getPoints() {
    }
}