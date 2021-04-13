package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private static final List<User> users = Arrays.asList(
        new User(1L, "Piet"),
        new User(2L, "Jozef"),
        new User (3L, "Anna Smith")
    );


    @GetMapping(path="/{userId}")
    public User getStudent(@PathVariable("userId") Long userId) {
        return users.stream()
                .filter(user -> userId.equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User " + userId + " does not exist"));
    }
}
