package com.socialgame.alpha.service;

import com.socialgame.alpha.configuration.security.jwt.JwtUtils;
import com.socialgame.alpha.domain.Role;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.ERole;
import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.JwtResponse;
import com.socialgame.alpha.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class StartServiceImplTest {

    @InjectMocks
    private final StartServiceImpl startService = new StartServiceImpl();

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    PasswordEncoder encoder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsImpl userDetailsImpl;

    @Mock
    private Authentication authentication;

    AuthenticationManager mockAuthenticationManager;
    Authentication mockAuthentication;
    UserDetailsImpl mockUserDetails;


    @BeforeEach
    void setUp() {
//        request = mock(HttpServletRequest.class);
//        mockPrincipal = mock(UserPrincipal.class);
//
//        when(request.getUserPrincipal()).thenReturn(mockPrincipal);
//        when(mockPrincipal.getName()).thenReturn("player1");
//        when(userRepository.findByUsername("player1")).thenReturn(Optional.ofNullable(user));
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

    @RepeatedTest(100)
    void generateGameIdString_shouldNeverReturnPlaceholder() {
        String gameIdString = startService.generateGameIdString();

        System.out.println(gameIdString);

        assertNotEquals("placeholder", gameIdString);
    }

//    @Test
//    void existingUserName_shouldReturnErrorResponse() {
//        CreateGameRequest request = new CreateGameRequest();
//        request.setUsername("takenUsername");
//
//        when(startService.generateGameIdString()).thenReturn("abc");
//
//
////        userRepository.
//
//        given(userRepository.existsByUsername("abc_takenUsername")).willReturn(true);
//
//        ResponseEntity<?> responseEntity = startService.createGame(request);
//
//        assertAll("Correct Answer Response",
//                () -> assertEquals(400, responseEntity.getStatusCodeValue()),
//                () -> assertTrue(responseEntity.getBody() instanceof ErrorResponse));
////                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("BAD_REQUEST"))
//
//
//    }

    @Test
    void createGame_shouldReturnJwtResponse() {

//        AuthenticationManager authenticationManager=mock(AuthenticationManager.class);
        Role gameHostRole = new Role(ERole.ROLE_GAMEHOST);
        Role playerRole = new Role(ERole.ROLE_PLAYER);

        CreateGameRequest request = new CreateGameRequest();
        request.setUsername("test");


        when(roleRepository.findByName(ERole.ROLE_GAMEHOST)).thenReturn(Optional.of(gameHostRole));
        when(roleRepository.findByName(ERole.ROLE_PLAYER)).thenReturn(Optional.of(playerRole));

        String gameIdString = startService.createGame(request);

        assertAll("Correct Answer Response",
                () -> assertTrue(gameIdString instanceof String),
                () -> assertEquals(3, gameIdString.length())
//                );
////                () -> assertTrue(((ErrorResponse) responseEntity.getBody()).getErrors().containsKey("BAD_REQUEST"))
        );
    }
}



