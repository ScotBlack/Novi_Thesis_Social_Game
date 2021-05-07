package com.socialgame.alpha.controller;

import com.socialgame.alpha.service.StartService;
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

@ExtendWith(MockitoExtension.class)
class StartControllerTest {

    @Mock
    StartService startService;

    @InjectMocks
    StartController startController;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(startController).build();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(startController);
    }

    @Test
    void shortAndLongUsername_shouldReturnBadRequest() {

    }

    @Test
    void createGame() {
    }

    @Test
    void joinGame() {
    }

    @Test
    void rejoinGame() {
    }

    @Test
    void handleValidationExceptions() {
    }
}