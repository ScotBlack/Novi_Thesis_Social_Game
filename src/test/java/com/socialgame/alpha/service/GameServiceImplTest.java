package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.LobbyResponse;
import com.socialgame.alpha.dto.response.PlayerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static com.socialgame.alpha.domain.enums.GameType.*;
import static com.socialgame.alpha.domain.enums.Color.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    Game game;
    Lobby lobby;

    User user1;
    User user2;
    User user3;
    User user4;
    Player player1;
    Player player2;
    Player player3;
    Player player4;

    HttpServletRequest httpRequest;
    UserPrincipal mockPrincipal;

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private PlayerRepository playerRepository;
    
    @BeforeEach
    void setUp() {
    user1 = new User();
    user1.setUsername("abc_player1");
    user1.setGameIdString("abc");

    user2 = new User();
    user2.setUsername("abc_player2");
    user2.setGameIdString("abc");

    user3 = new User();
    user3.setUsername("abc_player3");
    user3.setGameIdString("abc");

    user4 = new User();
    user4.setUsername("abc_player3");
    user4.setGameIdString("abc");

    game = new Game();
    game.setGameIdString("abc");
    game.setGameType(FFA);
    game.setScoreToWin(100);

    lobby = new Lobby();
    lobby.setGameIdString("abc");

    player1 = new Player();
    player1.setUser(user1);
    player1.setId(1L);
    player1.setName("player1");
    player1.setColor(RED);
    player1.setPhone(true);

    player2 = new Player();
    player2.setUser(user2);
    player2.setId(2L);
    player2.setName("player2");
    player2.setColor(BLUE);
    player2.setPhone(true);

    player3 = new Player();
    player3.setUser(user3);
    player3.setId(3L);
    player3.setName("player3");
    player3.setColor(GREEN);
    player3.setPhone(true);

    player4 = new Player();
    player4.setUser(user4);
    player4.setId(4L);
    player4.setName("player4");
    player4.setColor(YELLOW);
    player4.setPhone(true);

    game.setLobby(lobby);
    lobby.setGame(game);
    lobby.setPlayers(new HashSet<>());



    httpRequest = mock(HttpServletRequest.class);
    mockPrincipal = mock(UserPrincipal.class);

    }

    /** findPlayerById tests */
    @Test
    void notExistingPlayerId_shouldThrowException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            gameService.findPlayerByID(99L);
        });

        String expectedMessage = "Player with ID: " + "99" + " does not exist.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void findPlayerById_shouldReturnPlayerResponse() {
        when(playerRepository.findById(1L)).thenReturn(Optional.ofNullable(player1));

        ResponseEntity<?> response = gameService.findPlayerByID(1L);

        assertAll("Error Response startGame",
                () -> assertEquals(200, response.getStatusCodeValue()),
                () -> assertTrue(response.getBody() instanceof PlayerResponse),
                () -> assertEquals(Color.RED.toString(), ((PlayerResponse) response.getBody()).getColor()),
                () -> assertTrue(((PlayerResponse) response.getBody()).getPhone())
        );
    }

    /** lobbyStatusUpdate tests */

    @Test
    void notExistingUser_ShouldThrowException() {
        when(httpRequest.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("wrong_name");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            gameService.lobbyStatusUpdate(httpRequest);
        });

        String expectedMessage = "User with: " + "wrong_name" + " does not exist.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void notExistingGame_ShouldThrowException() {
        user1.setGameIdString("wrong");

        when(httpRequest.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("abc_player");
        when(userRepository.findByUsername("abc_player")).thenReturn(Optional.ofNullable(user1));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            gameService.lobbyStatusUpdate(httpRequest);
        });

        Exception exception2 = assertThrows(EntityNotFoundException.class, () -> {
            gameService.getPlayers("wrong");
        });

        Exception exception3 = assertThrows(EntityNotFoundException.class, () -> {
            gameService.getTeams("wrong");
        });

        Exception exception4 = assertThrows(EntityNotFoundException.class, () -> {
            gameService.getScore("wrong");
        });

        String expectedMessage = "Game with: " + "wrong" + " does not exist.";
        assertTrue(exception.getMessage().contains(expectedMessage));
        assertTrue(exception2.getMessage().contains(expectedMessage));
        assertTrue(exception3.getMessage().contains(expectedMessage));
        assertTrue(exception4.getMessage().contains(expectedMessage));
    }

    @Test
    void playerComposition_shouldReturnDifferentStatus() {
            System.out.println("|||LobbyStatus Test|||");

        when(httpRequest.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("abc_player");
        when(userRepository.findByUsername("abc_player")).thenReturn(Optional.ofNullable(user1));
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

    // One player
        lobby.getPlayers().add(player1);

        ResponseEntity<?> responseEntity1 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse1 = (LobbyResponse) responseEntity1.getBody();

        assertAll("LobbyStatus Response",
                () -> assertEquals(200, responseEntity1.getStatusCodeValue()),
                () -> assertEquals("Need at least 2 teams or players", lobbyResponse1.getStatus())
        );
            System.out.println("Test Complete: Need at least 2 teams or players");

    // -- 3 players // Classic // 2 Red Players // 1 Green Player
        game.setGameType(CLASSIC);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        player2.setColor(RED);

        ResponseEntity<?> responseEntity2 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse2 = (LobbyResponse) responseEntity2.getBody();

        Assertions.assertEquals("Every player needs it's own color", lobbyResponse2.getStatus());
            System.out.println("Test Complete: Every player needs it's own color");

    // -- 3 players // Classic // 1 Red Player // 1 Blue Player // 1 Green Player
        player2.setColor(BLUE);

        ResponseEntity<?> responseEntity3 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse3 = (LobbyResponse) responseEntity3.getBody();

        Assertions.assertEquals("Classic game can be started", lobbyResponse3.getStatus());
            System.out.println("Test Complete: Classic game can be started");

    // -- 3 players // FFA // 2 Players with Phone // 1 Player without Phone
        game.setGameType(FFA);
        player2.setPhone(false);

        ResponseEntity<?> responseEntity4 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse4 = (LobbyResponse) responseEntity4.getBody();

        Assertions.assertEquals("Every players need it's own phone", lobbyResponse4.getStatus());
            System.out.println("Test Complete: Every players need it's own phone");

    // -- 3 players // FFA // 3 Players with Phone // 2 Red Players  // 1 Green Player
        player2.setColor(RED);
        player2.setPhone(true);

        ResponseEntity<?> responseEntity5 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse5 = (LobbyResponse) responseEntity5.getBody();

        Assertions.assertEquals("Every players need it's own color", lobbyResponse5.getStatus());
            System.out.println("Test Complete: Every players need it's own color");


    // -- 3 players // FFA // 3 Players with Phone // 1 Red Player // 1 Blue Player // 1 Green Player
        player2.setColor(BLUE);

        ResponseEntity<?> responseEntity6 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse6 = (LobbyResponse) responseEntity6.getBody();

        Assertions.assertEquals("FFA game can be started", lobbyResponse6.getStatus());
            System.out.println("Test Complete: FFA game can be started");

    // -- 3 players // FFA // 3 Players with Phone // 2 Red Player // 1 Green Player
        game.setGameType(TEAMS);
        player2.setColor(RED);

        ResponseEntity<?> responseEntity7 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse7 = (LobbyResponse) responseEntity7.getBody();

        Assertions.assertEquals("Every team needs at least 2 players", lobbyResponse7.getStatus());
            System.out.println("Test Complete: Every team needs at least 2 players");

    // -- 4 players // FFA // Team/Color has 0 players with Phone
        lobby.getPlayers().add(player4);
        player4.setColor(GREEN);
        player1.setPhone(false);
        player2.setPhone(false);

        ResponseEntity<?> responseEntity8 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse8 = (LobbyResponse) responseEntity8.getBody();

        Assertions.assertEquals("Every team needs at least 1 player with phone", lobbyResponse8.getStatus());
            System.out.println("Test Complete: Every team needs at least 1 player with phone");

    // -- 4 players // FFA // Team/Color has 0 players with Phone
        player1.setPhone(true);


        ResponseEntity<?> responseEntity9 = gameService.lobbyStatusUpdate(httpRequest);
        LobbyResponse lobbyResponse9 = (LobbyResponse) responseEntity9.getBody();

        Assertions.assertEquals("Team game can be started", lobbyResponse9.getStatus());
        System.out.println("Test Complete: Team game can be started");
    }





}
