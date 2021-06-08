package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.LobbyResponse;
import com.socialgame.alpha.dto.response.PlayerResponse;
import com.socialgame.alpha.repository.*;
import com.socialgame.alpha.repository.minigame.QuestionRepository;
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
import java.util.*;

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
    Team team1;
    Team team2;

    Question question1;
    Question question2;
    Question question3;
    Question question4;

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

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private QuestionRepository questionRepository;
    
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

    team1 = new Team();
    team1.setId(1L);
    team1.setGame(game);
    team1.setName(RED);
    team1.setPlayers(new HashMap<>());
    team1.getPlayers().put(player1.getName(),100);

    team2 = new Team();
    team2.setId(2L);
    team2.setGame(game);
    team2.setName(BLUE);
    team2.setPlayers(new HashMap<>());
    team2.getPlayers().put(player2.getName(),100);

    game.setLobby(lobby);
    game.setTeams(new HashSet<>());
    game.getTeams().add(team1);
    game.getTeams().add(team2);
    lobby.setGame(game);
    lobby.setPlayers(new HashSet<>());

    question1 = new Question();
    question2 = new Question();
    question3 = new Question();
    question4 = new Question();

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
                () -> assertEquals(RED.toString(), ((PlayerResponse) response.getBody()).getColor()),
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
            gameService.getScore("wrong");
        });

        Exception exception4 = assertThrows(EntityNotFoundException.class, () -> {
            gameService.nextMiniGame("wrong");
        });

        String expectedMessage = "Game with: " + "wrong" + " does not exist.";
        assertTrue(exception.getMessage().contains(expectedMessage));
        assertTrue(exception2.getMessage().contains(expectedMessage));
        assertTrue(exception3.getMessage().contains(expectedMessage));
        assertTrue(exception4.getMessage().contains(expectedMessage));
    }

    @Test
    void playerComposition_shouldReturnDifferentStatus() {
        System.out.println("\n |||LobbyStatus Test|||");

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

    /** getPlayers test */
    @Test
    void foundGame_shouldReturnPlayerResponse() {
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        ResponseEntity<?> responseEntity = gameService.getPlayers("abc");

        assertAll("Error Response startGame",
                () -> assertEquals(200, responseEntity.getStatusCodeValue())
        );
    }

    /** getScore test */

    @Test
    void notStartedGame_shouldReturnErrorResponse() {
        game.setStarted(false);
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        ResponseEntity<?> responseEntity = gameService.getScore("abc");

        assertAll("Error Response startGame",
                () -> assertEquals(403, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("NOT_STARTED"))
        );
    }

    @Test
    void startedGame_shouldReturnTeamResponseSet() {
        game.setStarted(true);
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        ResponseEntity<?> responseEntity = gameService.getScore("abc");

        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

//    /** nextMiniGame tests */
//    @Test
//    void nextMiniGame_shouldResetAllTeamsHasAnsweredToFalse() {
//        System.out.println("\n |||NextMiniGame Test|||");
//
//        team1.setHasAnswered(true);
//        team2.setHasAnswered(true);
//
//        Set<String> answersSet =new HashSet<>();
//        answersSet.add("a");
//        answersSet.add("b");
//        answersSet.add("c");
//        answersSet.add("d");
//
//        List<Question> questionSet = new ArrayList<>();
//        questionSet.add(question1);
//        questionSet.add(question2);
//        questionSet.add(question3);
//        questionSet.add(question4);
//
//        question1.setAllAnswers(new HashSet<>());
//        question2.setAllAnswers(new HashSet<>());
//        question3.setAllAnswers(new HashSet<>());
//        question4.setAllAnswers(new HashSet<>());
//        question1.setAllAnswers(answersSet);
//        question2.setAllAnswers(answersSet);
//        question3.setAllAnswers(answersSet);
//        question4.setAllAnswers(answersSet);
//
//
//        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));
//        when(questionRepository.findAll()).thenReturn(questionSet);
//
//        gameService.nextMiniGame("abc");
//
//        Assertions.assertFalse(team1.getHasAnswered());
//            System.out.println("Test Complete: Team1 answered set False");
//        Assertions.assertFalse(team2.getHasAnswered());
//            System.out.println("Test Complete: Team2 answered set False");
//        Assertions.assertTrue(game.getCurrentCompetingTeams().contains(team1));
//        Assertions.assertTrue(game.getCurrentCompetingTeams().contains(team2));
//
//    }
}
