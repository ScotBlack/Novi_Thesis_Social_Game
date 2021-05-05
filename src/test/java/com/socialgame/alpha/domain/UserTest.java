package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User("Steve", "test");

        user.setId(1L);
        user.setUsername("Bob");
        user.setPassword("Password");
        user.setRoles(new HashSet<>());
        user.setPlayer(new Player());
    }

    @Test
    void userPropertiesTest() {
        assertAll("Properties Test",
            () -> assertAll("User Properties",
                () ->  assertEquals(1L, user.getId()),
                () ->  assertEquals("Bob", user.getUsername()),
                () ->  assertEquals("Password", user.getPassword()),
                () ->  assertTrue(user.getRoles() instanceof HashSet),
                () ->  assertTrue(user.getPlayer() instanceof Player)
            )
        );

    }
}
