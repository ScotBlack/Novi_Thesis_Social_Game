//package com.socialgame.alpha.service;
//
//import com.socialgame.alpha.dto.request.CreateGameRequest;
//import com.socialgame.alpha.dto.request.JoinGameRequest;
//import com.socialgame.alpha.dto.response.AuthGameResponse;
//import com.socialgame.alpha.dto.response.ErrorResponse;
//import com.socialgame.alpha.repository.*;
//import com.socialgame.alpha.security.jwt.JwtUtils;
//import java.util.*;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyString;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthorizationServiceImplTest {
//
//
//
//    @InjectMocks
//    AuthorizationServiceImpl authorizationService;
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private RoleRepository roleRepository;
//    @Mock
//    private LobbyRepository lobbyRepository;
//    @Mock
//    private GameRepository gameRepository;
//    @Mock
//    private PlayerRepository playerRepository;
//    @Mock
//    private AuthenticationManager authenticationManager;
//    @Mock
//    private PasswordEncoder encoder;
//    @Mock
//    private JwtUtils jwtUtils;
//    @BeforeEach
//    void setUp() {
//        CreateGameRequest createGameRequest = new CreateGameRequest();
//        createGameRequest.setUsername("testName");
//    }
//
//    @DisplayName("Single test successful")
//    @Test
//    void createGame_loopsUntilUniqueGameIdStringIsFound() {
//        CreateGameRequest createGameRequest = new CreateGameRequest();
//        createGameRequest.setUsername("testName");
//
//
//        Mockito.when(gameRepository.existsByGameIdString(anyString())).thenReturn(false);
//
//        // Act
//        ResponseEntity<?> responseEntity = authorizationService.createGame(createGameRequest);
//
//        //Assert
//        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
//        Assertions.assertTrue(responseEntity.getBody() instanceof AuthGameResponse);
//
//    }
//}
