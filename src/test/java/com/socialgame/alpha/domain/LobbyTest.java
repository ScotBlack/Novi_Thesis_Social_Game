package com.socialgame.alpha.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;



class LobbyTest {

    Lobby lobby;

    @BeforeEach
    void setUp() {
        lobby = new Lobby(new Player(), "def");

        lobby.setId(1L);
        lobby.setGameIdString("abc");
        lobby.setStatus("Test Status");
        lobby.setCanStart(true);
    }

    @Test
    void lobbyPropertiesTest() {
        assertEquals(1L, lobby.getId());
        assertEquals("abc", lobby.getGameIdString());
        assertTrue(lobby.getPlayers() instanceof HashSet);
        assertEquals("Test Status", lobby.getStatus());
        assertTrue(lobby.getCanStart());
    }
}
