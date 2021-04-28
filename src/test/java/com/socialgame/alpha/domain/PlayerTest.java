package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerTest {

    @Test
    void getId() {
        Player player = new Player("test", Color.RED, true);
        player.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Player.class,1L)).thenReturn(player);

        Long playerId = player.getId();

        assertEquals(1L, playerId);
    }

    @Test
    void getName() {
        Player player = new Player();
        player.setId(1L);
        player.setName("testName");

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Player.class,1L)).thenReturn(player);

        String name = player.getName();

        assertEquals("testName", name);
    }

    @Test
    void getColor() {
        Player player = new Player();
        player.setId(1L);
        player.setColor(Color.RED);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Player.class,1L)).thenReturn(player);

        Color color = player.getColor();

        assertEquals(Color.RED, color);
    }

    @Test
    void getPhone() {
        Player player = new Player();
        player.setId(1L);
        player.setPhone(true);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Player.class,1L)).thenReturn(player);

        Boolean phone = player.getPhone();

        assertEquals(true, phone);
    }

    @Test
    void getLobby() {
        Player player = new Player();
        player.setId(1L);
        player.setLobby(new Lobby());

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Player.class,1L)).thenReturn(player);

        Lobby lobby = player.getLobby();

        assertTrue(lobby instanceof Lobby);
    }
}