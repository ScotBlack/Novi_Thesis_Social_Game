package com.socialgame.alpha.domain;

import com.socialgame.alpha.prototype.GamePrototype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class LobbyTest {

    @Test
    void getId() {
        Player player = new Player();
        Lobby lobby = new Lobby(player, "abc");
        lobby.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Lobby.class,1L)).thenReturn(lobby);

        Long lobbyId = lobby.getId();

        assertEquals(1L, lobbyId );
    }

    @Test
    void getGameIdString() {
        Player player = new Player();
        Lobby lobby = new Lobby(player, "abc");
        lobby.setId(1L);
        lobby.setGameIdString("def");

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Lobby.class,1L)).thenReturn(lobby);

        String gameIdString = lobby.getGameIdString();

        assertEquals("def", gameIdString );
    }

    @Test
    void getGame() {
        Player player = new Player();
        Lobby lobby = new Lobby(player, "abc");
        lobby.setId(1L);
        lobby.setGame(GamePrototype.protoGame());

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Lobby.class,1L)).thenReturn(lobby);

        Game game = lobby.getGame();;

        assertTrue(game instanceof Game);
    }

    @Test
    void getPlayers() {
        Player player = new Player();
        Lobby lobby = new Lobby(player, "abc");
        lobby.setId(1L);
        lobby.setPlayers(new HashSet<>());

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Lobby.class,1L)).thenReturn(lobby);

        Set<Player> players = lobby.getPlayers();

        assertTrue(players instanceof HashSet);
    }

    @Test
    void getStatus() {
        Player player = new Player();
        Lobby lobby = new Lobby(player, "abc");
        lobby.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Lobby.class,1L)).thenReturn(lobby);

        lobby.setStatus("test result");
        String status = lobby.getStatus();

        assertEquals("test result", status );
    }

    @Test
    void getCanStart() {
        Lobby lobby = new Lobby();
        lobby.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Lobby.class,1L)).thenReturn(lobby);

        lobby.setCanStart(true);
        Boolean canStart = lobby.getCanStart();

        assertEquals(true, canStart );
    }
}