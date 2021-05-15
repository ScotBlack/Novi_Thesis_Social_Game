package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    Team team;

    @BeforeEach
    void setUp() {
        team = new Team(new Game(), Color.BLUE);

        team.setId(1L);
        team.setGame(new Game());
        team.setName(Color.RED);
        team.setPlayers(new HashMap<>());
        team.setPoints(50);
    }

   @Test
    void teamPropertiesTest() {
       assertAll("Properties Test",
           () -> assertAll("Team Properties",
//                   () ->  assertEquals(1L, team.getId()),
                   () ->  assertTrue(team.getGame() instanceof Game),
                   () ->  assertEquals(Color.RED, team.getName()),
                   () ->  assertTrue(team.getPlayers() instanceof HashMap),
                   () ->  assertEquals(50,  team.getPoints())
           )
       );
   }
}