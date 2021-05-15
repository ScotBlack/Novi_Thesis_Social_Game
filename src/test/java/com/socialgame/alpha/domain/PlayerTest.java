package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.domain.minigame.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerTest {
    Player player;

    @BeforeEach
    void setUp() {
        User user = new User();
        Lobby lobby = new Lobby();
        player = new Player(user, "Bob", Color.BLUE, false);

        player.setId(1L);
        player.setUser(user);
        player.setName("Steve");
        player.setColor(Color.RED);
        player.setPhone(true);
        player.setLobby(lobby);
    }


    @Test
    void propertiesTest() {
        assertAll("Properties Test",
                () -> assertAll("Player Properties",
//                        () ->  assertEquals(1L, player.getId()),
                        () ->  assertTrue(player.getUser() instanceof User),
                        () ->  assertEquals("Steve", player.getName()),
                        () ->  assertEquals(Color.RED, player.getColor()),
                        () ->  assertTrue(player.getPhone()),
                        () ->  assertTrue(player.getLobby() instanceof Lobby)
                )
        );
    }
}