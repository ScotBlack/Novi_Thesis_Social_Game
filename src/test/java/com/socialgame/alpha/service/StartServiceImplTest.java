package com.socialgame.alpha.service;


import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Role;
import com.socialgame.alpha.domain.enums.ERole;
import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StartServiceImplTest {

    @InjectMocks
    private final StartServiceImpl startService = new StartServiceImpl();

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    Lobby lobby;
    Game game;

    JoinGameRequest joinRequest;

    Role gameHostRole = new Role(ERole.ROLE_GAMEHOST);
    Role playerRole = new Role(ERole.ROLE_PLAYER);

    @BeforeEach
    void setUp() {
        lobby = new Lobby();
        game = new Game();
        game.setStarted(false);

        joinRequest = new JoinGameRequest();
        joinRequest.setUsername("test");
        joinRequest.setGameIdString("abc");
    }

//    @Test
//    void authenticateUser_shouldReturnJwtResponse() {
//        mockAuthenticationManager = mock(AuthenticationManager.class);
//        mockUserDetails = mock(UserDetailsImpl.class);
//
//        when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                "test",
//                "test"))).thenReturn(mockAuthentication);
//
//        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
//
//
//        JwtResponse jwt = startService.authenticateUser("test", "test");
//    }

    /** generate gameIdString test */
    @RepeatedTest(100)
    void generateGameIdString_shouldNeverReturnPlaceholder() {
        String gameIdString = startService.generateGameIdString();

        System.out.println(gameIdString);

        assertNotEquals("placeholder", gameIdString);
    }


    /** initializeGame Tests */
    @Test
    void createGame_shouldReturnGameIdString() {
        CreateGameRequest request = new CreateGameRequest();
        request.setUsername("test");

        when(roleRepository.findByName(ERole.ROLE_GAMEHOST)).thenReturn(Optional.of(gameHostRole));
        when(roleRepository.findByName(ERole.ROLE_PLAYER)).thenReturn(Optional.of(playerRole));

        String gameIdString = startService.initializeGame(request);
        boolean onlyLetters = true;

        char[] chars = gameIdString.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
               onlyLetters = false;
            }
        }

        assertTrue(onlyLetters);
        assertEquals(3, gameIdString.length());
    }

    /** initializePlayer test */
    @Test
    void notExistingLobby_shouldThrowException() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            startService.initializePlayer(joinRequest);
        });

        String expectedMessage = "Lobby with: " + joinRequest.getGameIdString() + " does not exist.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void notExistingGame_shouldThrowException() {
        when(lobbyRepository.findByGameIdString(joinRequest.getGameIdString())).thenReturn(Optional.of(lobby));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            startService.initializePlayer(joinRequest);
        });

        String expectedMessage = "Game with: " + joinRequest.getGameIdString() + " does not exist.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void joiningStartedGame_shouldThrowException() {
        game.setStarted(true);

        when(lobbyRepository.findByGameIdString(joinRequest.getGameIdString())).thenReturn(Optional.of(lobby));
        when(gameRepository.findByGameIdString(joinRequest.getGameIdString())).thenReturn(Optional.of(game));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            startService.initializePlayer(joinRequest);
        });

        String expectedMessage = "You can't join, game has already started.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void secondPlayerInLobby_shouldSetPlayerColorBlue() {
        String expectedUsername = joinRequest.getGameIdString() + "_" + joinRequest.getUsername();

        lobby.setPlayers(new HashSet<>());

        when(lobbyRepository.findByGameIdString(joinRequest.getGameIdString())).thenReturn(Optional.of(lobby));
        when(gameRepository.findByGameIdString(joinRequest.getGameIdString())).thenReturn(Optional.of(game));
        when(roleRepository.findByName(ERole.ROLE_PLAYER)).thenReturn(Optional.of(playerRole));

        String username = startService.initializePlayer(joinRequest);

        assertEquals(username, expectedUsername);
    }
}




