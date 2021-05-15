package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.dto.response.ErrorResponse;
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
    private PlayerRepository playerRepository;
    
    @Mock
    private UserRepository userRepository;

    Player player1;
    Player player2;
    Team team1;
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

        team1 = new Team();
        team1.setName(Color.BLUE);

        user.setPlayer(player1);
        user.setTeam();

        player2 = new Player();

        request = mock(HttpServletRequest.class);
        mockPrincipal = mock(UserPrincipal.class);
    }


    @Test
    void notExistingPlayerId_ShouldThrowException()   {
        Long player2 = 2L;

        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            playerService.togglePlayerColor(player2, request);
        });

        String expectedMessage = "Player with ID: " + player2 + " does not exist.";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void notMatchingPlayers_ShouldReturnErrorResponse() {
        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));
        when(playerRepository.findById(2L)).thenReturn(Optional.ofNullable(player2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            playerService.togglePlayerColor(2L, request);
        });

        String expectedMessage = "You may only change your own color";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }


    @Test
    void shouldReturnNextColor() {
        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("player1");
        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));
        when(playerRepository.findById(1L)).thenReturn(Optional.ofNullable(player1));

        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(1L, request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(player1.getColor(), Color.BLUE);
    }




//
//
//
//
////    @Test
////    void notExistingPlayerId_ShouldReturnErrors()  {
////        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
////        when(mockPrincipal.getName()).thenReturn("player1");
////        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));
////
////        when(playerRepository.findById(2L)).thenReturn(Optional.empty());
////
////        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(2L, request);
////
////        Assertions.assertThrows(IllegalArgumentException.class, () -> Integer.parseInt("One"));
////    }
//
//



//
//    @Test
//    void matchingPlayers_ShouldReturnStatusOk() {
//        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
//        when(mockPrincipal.getName()).thenReturn("player1");
//        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));
//
//        user.setPlayer(player1);
//        when(playerRepository.findById(2L)).thenReturn(Optional.ofNullable(player1));
//
//        ResponseEntity<?> responseEntity = playerService.togglePlayerColor(2L, request);
//
//        assertEquals(200, responseEntity.getStatusCodeValue());
//    }
//
//
//    // teamAnswer



}
