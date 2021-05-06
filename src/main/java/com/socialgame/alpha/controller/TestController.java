package com.socialgame.alpha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(path="/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("test");
    }
}
