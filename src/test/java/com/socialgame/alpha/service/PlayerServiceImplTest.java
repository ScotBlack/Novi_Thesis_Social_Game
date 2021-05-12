package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;


import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @InjectMocks
    private final PlayerService playerService = new PlayerServiceImpl();

    @Mock
    private PlayerRepository playerRepository;
    
    @Mock
    private UserRepository userRepository;

//    private JoinGameRequest newPlayerRequest;

    Player player1;
    Player player2;
    User user;
    HttpServletRequest request;
    UserPrincipal mockPrincipal;


    @BeforeEach
    void setUp() {
        user = new User();

        player1 = new Player();
        player1.setUser(user);
        player1.setId(1L);
        player1.setName("player1");
        player1.setColor(Color.RED);
        player1.setPhone(true);

        user.setPlayer(player1);

        player2 = new Player();

        request = mock(HttpServletRequest.class);
        mockPrincipal = mock(UserPrincipal.class);
    }

    @Test
    void notExistingPlayerId_ShouldReturnError() {
        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));

        when(playerRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(2L, request);

        assertAll("Error Response togglePlayerColor()",
                () -> assertEquals(404, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("ENTITY_NOT_FOUND")),
                () -> assertEquals("Player with ID: 2 does not exist.", ((ErrorResponse) responseEntity.getBody()).getErrors().get("ENTITY_NOT_FOUND"))
        );
    }

    @Test
    void notExistingUser_ShouldReturnError() {
        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");

        when(userRepository.findByUsername("player1")).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(2L, request);

        assertAll("Error Response togglePlayerColor()",
                () -> assertEquals(404, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("USER_NOT_FOUND")),
                () -> assertEquals("User with: " + "player1" + " does not exist.", ((ErrorResponse) responseEntity.getBody()).getErrors().get("USER_NOT_FOUND"))
        );
    }

    
    @Test
//    @WithMockUser(username="username",roles={"PLAYER"})
    void notMatchingPlayers_ShouldReturnErrorResponse() {
        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));

        when(playerRepository.findById(2L)).thenReturn(Optional.ofNullable(player2));

        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(2L, request);

        assertAll("Error Response togglePlayerColor()",
                () -> assertEquals(400, responseEntity.getStatusCodeValue()),
                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse),
                () -> assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size()),
                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("BAD_REQUEST"))
        );
    }

    @Test
    void matchingPlayers_ShouldReturnStatusOk() {
        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));

        user.setPlayer(player1);
        when(playerRepository.findById(2L)).thenReturn(Optional.ofNullable(player1));

        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(2L, request);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }


    @Test
    void phoneMustTrueOrFalse_ElseReturnError () {



    }


}
