package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/account")
public class UserController {

//    private static final List<User> USERS = Arrays.asList(
//        new User(51L, "Piet"),
//        new User(52L, "Jozef"),
//        new User(53L, "Anna Smith")
//    );
//
//    @GetMapping(path="/{userId}")
//    public User getUser(@PathVariable("userId") Long userId) {
//        return USERS.stream()
//                .filter(user -> userId.equals(user.getUserId()))
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("User " + userId + " does not exist"));
//    }
}
