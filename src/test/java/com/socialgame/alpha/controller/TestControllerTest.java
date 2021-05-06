package com.socialgame.alpha.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import com.socialgame.alpha.service.TestService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
class TestControllerTest {


//    @Autowired
//    private WebApplicationContext context;

    @Mock
    TestService testService;

    @InjectMocks
    TestController controller;

    MockMvc mvc;


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }

    @Test
    void testTest() throws Exception {
//        given();

        mvc
                .perform(get("/test/test"))
                .andExpect(status().isOk());
    }


//    @Test
//    @WithMockUser(roles="ADMIN")
//    void test() throws Exception {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .defaultRequest(get("/").with(user("user").roles("ADMIN")))
//                .apply(springSecurity())
//                .build();
//        mvc
//                .perform(formLogin("/test/test").user("user").password("pass"))
//
//        .andExpect(unauthenticated());
//
//    }

//
//    @Test
//    @WithMockUser(roles="ADMIN")
//    void test2() throws Exception {
//        mvc
//                .perform(formLogin());
//    }
}