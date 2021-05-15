package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.dto.request.TeamAnswerRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.ResponseBuilder;
import com.socialgame.alpha.dto.response.minigame.TeamAnswerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import com.socialgame.alpha.repository.UserRepository;

import com.socialgame.alpha.repository.minigame.MiniGameBaseRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {


    @InjectMocks
    private final PlayerService playerService = new PlayerServiceImpl();

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ResponseBuilder responseBuilder;

    @Mock private TeamRepository teamRepository;
    


    Game game;
    User user;
    Player player1;
    Player player2;
    Team team1;

    Question question;

    TeamAnswerRequest answerRequest;
    HttpServletRequest request;
    UserPrincipal mockPrincipal;


    @BeforeEach
    void setUp() {
        game = new Game();
        game.setStarted(true);
        game.setCurrentCompetingTeams(new HashSet<>());


        user = new User();

        player1 = new Player();
        player1.setUser(user);
        player1.setId(1L);
        player1.setName("player1");
        player1.setColor(Color.RED);
        player1.setPhone(true);

        team1 = new Team();
        team1.setName(Color.BLUE);
        team1.setPoints(0);
        team1.setHasAnswered(false);
        game.getCurrentCompetingTeams().add(team1);

        user.setGameIdString("abc");
        user.setPlayer(player1);
        user.setTeam(team1);

        question = new Question();
        question.setCorrectAnswer("Lima");
        question.setPoints(10);
        question.setMiniGameType(MiniGameType.QUESTION);
        game.setCurrentMiniGame(question);

        player2 = new Player();

        answerRequest = new TeamAnswerRequest();
        answerRequest.setAnswer("Lima");
        request = mock(HttpServletRequest.class);
        mockPrincipal = mock(UserPrincipal.class);

        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));

    }

    /** TogglePlayerColor Tests */
    @Test
    void notExistingPlayerId_ShouldThrowException()   {
        Long player2 = 2L;

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            playerService.togglePlayerColor(player2, request);
        });

        String expectedMessage = "Player with ID: " + player2 + " does not exist.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void notMatchingPlayers_ShouldReturnErrorResponse() {
        when(playerRepository.findById(2L)).thenReturn(Optional.ofNullable(player2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.togglePlayerColor(2L, request);
        });

        String expectedMessage = "You may only change your own color";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }


    @Test
    void shouldReturnNextColor() {
        when(playerRepository.findById(1L)).thenReturn(Optional.ofNullable(player1));

        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(1L, request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(player1.getColor(), Color.BLUE);
    }

//    @Test
//    void shouldReturnRedColor_byDefault() {
//
//    }


    /** TeamAnswer Tests */
    @Test
    void notExistingGameIdString_shouldThrowException() {
        String gameIdString = "def";
        user.setGameIdString(gameIdString);
//        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));


//        when(gameRepository.findByGameIdString("abc").thenReturn

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            playerService.teamAnswer(request, answerRequest);
        });

        String expectedMessage = "Game with: " + gameIdString + " does not exist.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void notStartedGame_shouldThrowException() {
        game.setStarted(false);
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.teamAnswer(request, answerRequest);
        });

        String expectedMessage = "Game has not yet started.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

//    @Test
//    void userWithNoSetTeam_shouldThrowException() {
//        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));
//
//        Exception exception = assertThrows(NullPointerException.class, () -> {
//            playerService.teamAnswer(request, answerRequest);
//        });
//
//        String expectedMessage = "User doesn't have any team assigned, fatal error for game.";
//
//        assertTrue(exception.getMessage().contains(expectedMessage));
//    }

    @Test
    void notCompetingTeam_shouldReturnException() {
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        game.getCurrentCompetingTeams().remove(team1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.teamAnswer(request, answerRequest);
        });

        String expectedMessage = "Team: " + team1.getName() + " does not compete in this MiniGame.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test // avoid multiple answering to same question
    void ifTeamHasAnswered_shouldReturnException() {
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        team1.setHasAnswered(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.teamAnswer(request, answerRequest);
        });

        String expectedMessage = "Team: " + team1.getName() + " has already answered.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void notYetDeployedMiniGameType_ShouldReturnStatus400() {
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        question.setMiniGameType(MiniGameType.BEST_ANSWER);

        ResponseEntity<?> responseEntity = playerService.teamAnswer(request, answerRequest);

        assertAll("Correct Answer Response",
                () -> assertEquals(400, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("BAD_REQUEST"))

        );
    }


    @Test
    void correctAnswer_shouldIncreaseTeamPointsAndSetHasAnsweredTrue() {
        TeamAnswerResponse answerResponse =
                new TeamAnswerResponse(
                        team1.getId(),
                        question.getQuestion(),
                        answerRequest.getAnswer(),
                        true,
                        10,
                        10
                );

        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        int initialPoints = team1.getPoints();

        ResponseEntity<?> responseEntity = playerService.teamAnswer(request, answerRequest);

        assertAll("Correct Answer Response",
                () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                () -> assertEquals(team1.getPoints(), initialPoints + question.getPoints()),
                () -> assertTrue(team1.getHasAnswered())
        );
    }

    @Test
    void incorrectAnswer_shouldSetHasAnsweredTrue() {
        when(gameRepository.findByGameIdString("abc")).thenReturn(Optional.ofNullable(game));

        answerRequest.setAnswer("wrong");
        int initialPoints = team1.getPoints();

        ResponseEntity<?> responseEntity = playerService.teamAnswer(request, answerRequest);

        assertAll("Correct Answer Response",
                () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                () -> assertEquals(team1.getPoints(), initialPoints),
                () -> assertTrue(team1.getHasAnswered())
        );
    }








}
