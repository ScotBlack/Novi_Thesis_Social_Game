package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.enums.GameType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceImplTest {

    @InjectMocks
    private final HostService hostService = new HostServiceImpl();


    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId(1l);

    }

    @Test
    void invalidGameTypeShouldReturnErrorResponse() {



        ResponseEntity<?> responseEntity = hostService.setGameType(1L, GameType.CLASSIC);

    }



    @Test
    void setLobbyRepository() {
    }

    @Test
    void setGameRepository() {
    }

    @Test
    void setPlayerRepository() {
    }

    @Test
    void setTeamRepository() {
    }

    @Test
    void toggleOtherPlayerColor() {

        assertAll("Test Prop Set:",
                () -> assertEquals(2,3, "Test failed lol idiot"),
                () -> assertEquals("test", "test"));
    }

    @Test
    void setGameType() {
    }

    @Test
    void setPoints() {
    }

    @Test
    void startGame() {
    }

    @Test
    void createResponseObject() {
    }

    @Test
    void testCreateResponseObject() {
    }
}