package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.SetGamePointsRequest;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.TeamResponse;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    Lobby lobby;
    SetGameTypeRequest setGameTypeRequest;
    SetGamePointsRequest setGamePointsRequest;

    @BeforeEach
    void setUp() {
        lobby = new Lobby();
        lobby.setCanStart(false);

        game = new Game();
        game.setId(1l);
        game.setGameIdString("abc");
        game.setGameType(GameType.FFA);
        game.setLobby(lobby);
        game.setTeams(new HashSet<>());

        setGameTypeRequest = new SetGameTypeRequest();
        setGamePointsRequest = new SetGamePointsRequest();
        System.out.println(game.getGameIdString());
    }

    @Test
    void invalidGameIdStringShouldReturnErrorResponse() {
        setGameTypeRequest.setGameIdString("wrong");
        setGamePointsRequest.setGameIdString("wrong");

        Mockito.when(gameRepository.findByGameIdString("wrong")).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = hostService.setGameType(setGameTypeRequest);
        ResponseEntity<?> responseEntity2 = hostService.setPoints(setGamePointsRequest);
        ResponseEntity<?> responseEntity3 = hostService.startGame("wrong");

        assertAll("Error Response setGameType Properties",
                () -> assertEquals(404, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity instanceof ResponseEntity<?>),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("ENTITY_NOT_FOUND"))
        );
        assertAll("Error Response setGamePoint Properties",
                () -> assertEquals(404, responseEntity2.getStatusCodeValue()),
                () -> assertTrue(responseEntity2 instanceof ResponseEntity<?>),
                () -> assertTrue(responseEntity2.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity2.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity2.getBody()).getErrors().containsKey("ENTITY_NOT_FOUND"))
        );
        assertAll("Error Response setGamePoint Properties",
                () -> assertEquals(404, responseEntity3.getStatusCodeValue()),
                () -> assertTrue(responseEntity3 instanceof ResponseEntity<?>),
                () -> assertTrue(responseEntity3.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity3.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity3.getBody()).getErrors().containsKey("ENTITY_NOT_FOUND"))
        );
    }

    @Test
    void startGame_ShouldReturnErrorResponse_WhenLobbyCanStartIsFalse() {
        Mockito.when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        ResponseEntity<?> responseEntity = hostService.startGame("abc");

        assertAll("Error Response startGame",
                () -> assertEquals(403, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity instanceof ResponseEntity<?>),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("NOT_READY"))
        );
    }

    @Test
    void startGame_ShouldSetGameStarted_WhenLobbyCanStartIsTrue() {
        lobby.setCanStart(true);
        lobby.setPlayers(new HashSet<Player>());
        Mockito.when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));


        ResponseEntity<?> responseEntity = hostService.startGame("abc");

        assertEquals(200, responseEntity.getStatusCodeValue());

        assertAll("Error Response startGame",
                () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity instanceof ResponseEntity<?>),
                () -> assertTrue(responseEntity.getBody() instanceof HashSet)
        );
    }
}