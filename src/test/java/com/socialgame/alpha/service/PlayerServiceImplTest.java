package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.NewPlayerRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.repository.PlayerRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @InjectMocks
    private final PlayerService playerService = new PlayerServiceImpl();

    @Mock
    private PlayerRepository playerRepository;

    private NewPlayerRequest newPlayerRequest;

    @BeforeEach
    void setUp() {
        newPlayerRequest = new NewPlayerRequest();
        newPlayerRequest.setName("Scot");
        newPlayerRequest.setPhone("true");
    }

    @Test
    void notExistingId_ShouldReturnError() {
        //Arrange
        Long id = 2L;
        Mockito.when(playerRepository.findById(id)).thenReturn(Optional.empty());


        // Act
        ResponseEntity<?> responseEntity = playerService.findPlayerByID(id);

        //Assert
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        Assertions.assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size());
    }


    @Test
    void phoneMustTrueOrFalse_ElseReturnError () {



    }


}