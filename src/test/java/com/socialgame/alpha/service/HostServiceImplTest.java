package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameSetting;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.SetGamePointsRequest;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import com.socialgame.alpha.dto.request.SettingRequest;
import com.socialgame.alpha.dto.request.TeamAnswerRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.TeamResponse;
import com.socialgame.alpha.prototype.GamePrototype;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import com.socialgame.alpha.repository.UserRepository;
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
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipal;
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
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    User user;
    Game game;
    Lobby lobby;
    Player player1;
    Player player2;

    SettingRequest settingRequest1;
    SettingRequest settingRequest2;

    HttpServletRequest httpRequest;
    UserPrincipal mockPrincipal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setGameIdString("abc");

        lobby = new Lobby();
        lobby.setGameIdString("abc");
        lobby.setCanStart(false);
        lobby.setStatus("Test status.");


        game = new Game();
        game.setId(1l);
        game.setGameIdString("abc");
        game.setGameType(GameType.FFA);
        game.setLobby(lobby);
        game.setTeams(new HashSet<>());

        player1 = new Player();
        player1.setUser(user);
        player1.setId(1L);
        player1.setName("player1");
        player1.setColor(Color.RED);
        player1.setPhone(true);

        player2 = new Player();
        player2.setUser(user);
        player2.setId(1L);
        player2.setName("player2");
        player2.setColor(Color.BLUE);
        player2.setPhone(true);

        lobby.setGame(game);
        lobby.setPlayers(new HashSet<>());
        lobby.getPlayers().add(player1);
        lobby.getPlayers().add(player2);


        httpRequest = mock(HttpServletRequest.class);
        mockPrincipal = mock(UserPrincipal.class);

        settingRequest1 = new SettingRequest();
        settingRequest2 = new SettingRequest();
    }

    @Test
    void invalidGameIdString_ShouldThrowException() {
        settingRequest1.setGameIdString("wrong");
        settingRequest2.setGameIdString("wrong");

        Exception exception1 = assertThrows(EntityNotFoundException.class, () -> {
            hostService.setGameSetting(settingRequest1);
        });

        String expectedMessage1 = "Game with: " + settingRequest1.getGameIdString() + " does not exist.";
        assertTrue(exception1.getMessage().contains(expectedMessage1));

        Exception exception2 = assertThrows(EntityNotFoundException.class, () -> {
            hostService.setGameSetting(settingRequest2);
        });

        String expectedMessage2 = "Game with: " + settingRequest2.getGameIdString() + " does not exist.";
        assertTrue(exception2.getMessage().contains(expectedMessage2));
    }

    @Test
    void methodShouldSwitchBetween_ScoreToWin_GameType() {
        settingRequest1.setGameIdString("abc");
        settingRequest1.setSetting(GameSetting.SCORE_TO_WIN);
        settingRequest1.setScoreToWin(200);

        settingRequest2.setGameIdString("abc");
        settingRequest2.setSetting(GameSetting.GAME_TYPE);
        settingRequest2.setGameType(GameType.CLASSIC);

        when(gameRepository.findByGameIdString(settingRequest1.getGameIdString())).thenReturn(Optional.ofNullable(game));
        when(gameRepository.findByGameIdString(settingRequest2.getGameIdString())).thenReturn(Optional.ofNullable(game));

        hostService.setGameSetting(settingRequest1);
        assertEquals(200, game.getScoreToWin());

        hostService.setGameSetting(settingRequest2);
        assertEquals(GameType.CLASSIC, game.getGameType());
    }

    /** startGame tests */
    @Test
    void notExistingUsername_ShouldThrowException() {
        when(httpRequest.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            hostService.startGame(httpRequest);
        });

        String expectedMessage = "User with: " + "player1" + " does not exist.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void invalidGameIdString_shouldThrowException() {
        when(httpRequest.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            hostService.startGame(httpRequest);
        });

        String expectedMessage = "Game with: " + "abc" + " does not exist.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ifLobbyCanStartIsFalse_shouldReturnErrorResponse() {
        when(httpRequest.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        ResponseEntity<?> responseEntity = hostService.startGame(httpRequest);

        assertAll("Error Response startGame",
                () -> assertEquals(403, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("NOT_READY"))
        );
    }

    @Test
    void startGame_ShouldSetGameStarted_WhenLobbyCanStartIsTrue() {
        when(httpRequest.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        lobby.setCanStart(true);
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        ResponseEntity<?> responseEntity = hostService.startGame(httpRequest);

        assertAll("Error Response startGame",
                () -> assertEquals(404, responseEntity.getStatusCodeValue())
//                () -> assertTrue(responseEntity instanceof ResponseEntity<?>),
//                () -> assertTrue(responseEntity.getBody() instanceof HashSet)
        );
    }
}