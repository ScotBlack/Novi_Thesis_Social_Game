package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @InjectMocks
    private final PlayerService playerService = new PlayerServiceImpl();

    @Mock
    private PlayerRepository playerRepository;


    @Test
    void notExistingId_ShouldReturnError() {
        //Arrange
        Long id = 9999L;

        // Act
        ResponseEntity<?> responseEntity = playerService.findPlayerByID(id);

        //Assert
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        Assertions.assertEquals(1, ((ErrorResponse) responseEntity.getBody()).getErrors().size());
    }
}
