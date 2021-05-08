//package com.socialgame.alpha.controller;
//
//import com.socialgame.alpha.service.AuthorizationService;
//import com.socialgame.alpha.service.AuthorizationServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//class AuthControllerTest {
//
//    @Autowired
//    private AuthorizationServiceImpl authorizationService;
//
////    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
////    @SuppressWarnings("SpringJavaAutowiringInspection")
////    @Autowired
//    @Autowired
//    AuthController authController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void contextLoads() {
//        Assertions.assertNotNull(authController);
//    }
//
//    @Test
//    void whenUsernameTooShort_AtCreateGame_mustGiveError() throws Exception {
//        String username = "{" +
//                "    \"username\":\"n\" " +
//                "}";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/creategame")
//            .content(username)
//            .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//
//    }
//
//
//
//    @Test
//    void createGame() {
//    }
//
//    @Test
//    void joinGame() {
//    }
//
//    @Test
//    void rejoinGame() {
//    }
//}