package com.socialgame.alpha.controller;

import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.service.StartService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class StartControllerTest {

    @Mock
    StartService startService;

    @InjectMocks
    StartController startController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(startController).build();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(startController);
    }

    @Test
    void shortUsername_shouldReturnBadRequest() throws Exception {
        String userCreate = "{" +
                "    \"username\":\"he\"" +
                "}";

        mockMvc.perform(post("/api/start/creategame").accept("application/json")
                .content(userCreate)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        String userJoin = "{" +
                "    \"username\":\"he\"," +
                "    \"gameIdString\":\"gameIdString\"" +
                "}";


        mockMvc.perform(post("/api/start/joingame").accept("application/json")
                .content(userJoin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void longUsername_shouldReturnBadRequest() throws Exception {
        String userCreate = "{" +
                "    \"username\":\"hellomynameisfartoolongtopassasusernameforthisapp\"" +
                "}";

        mockMvc.perform(post("/api/start/creategame").accept("application/json")
            .content(userCreate)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        String userJoin = "{" +
                "    \"username\":\"hellomynameisfartoolongtopassasusernameforthisapp\"," +
                "    \"gameIdString\":\"gameIdString\"" +
                "}";


        mockMvc.perform(post("/api/start/joingame").accept("application/json")
                .content(userJoin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void correctUsername_shouldReturnOkRequest() throws Exception {
        String user = "{" +
                "    \"username\":\"correct\"" +
                "}";

        mockMvc.perform(post("/api/start/creategame")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String userJoin = "{" +
                "    \"username\":\"correct\"," +
                "    \"gameIdString\":\"gameIdString\"" +
                "}";


        mockMvc.perform(post("/api/start/joingame").accept("application/json")
                .content(userJoin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

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