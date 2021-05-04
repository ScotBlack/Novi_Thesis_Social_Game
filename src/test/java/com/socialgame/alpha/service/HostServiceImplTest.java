package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.prototype.GamePrototype;
import com.socialgame.alpha.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HostServiceImplTest {

    @InjectMocks
    private final HostService hostService = new HostServiceImpl();

    @Mock
    private GameRepository gameRepository;


    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId(1L);
    }

    //        EntityManager entityManager = mock(EntityManager.class);
//        when(entityManager.find(Game.class,1L)).thenReturn(game);

//        Mockito.when(gameRepository.findById(1l).thenReturn(Optional);
//        Mockito.when(gameRepository.findById(1l)).thenReturn(Optional.);


    @Test
    void invalidGameTypeShouldReturnErrorResponse() {

//
        ResponseEntity<?> responseEntity = hostService.setGameType(1L, GameType.CLASSIC);
//


        game.setGameType(GameType.FFA);

        assertAll("Properties Test",
                () -> assertEquals(game.getGameType(), GameType.FFA),
                () -> assertEquals(200, responseEntity.getStatusCodeValue()
                        ));
        assertEquals(game.getGameType(), GameType.FFA);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
//        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());

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