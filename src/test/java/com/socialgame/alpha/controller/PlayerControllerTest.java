package com.socialgame.alpha.controller;

import com.socialgame.alpha.service.PlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    PlayerService playerService;

    @InjectMocks
    PlayerController playerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(playerController);
    }
    
    @Test
    void returnsErrorResponse_whenIdNotFound() throws Exception {
        mockMvc.perform(get("/api/player/{id}/toggle")
                .param("id", String.valueOf(8L)))
            .andExpect(model().attributeDoesNotExist());
    }

    @Test
    void togglePlayerColor() {
    }

    @Test
    void teamAnswer() {
    }

    @Test
    void handleValidationExceptions() {
    }
}