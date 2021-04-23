package com.socialgame.alpha.controller;

import com.socialgame.alpha.dto.request.LoginRequest;
import com.socialgame.alpha.dto.request.SignupRequest;
import com.socialgame.alpha.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    //ResponseEntity<JwtResponse>
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authorizationService.authenticateUser(loginRequest);
    }


    //ResponseEntity<MessageResponse>
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return authorizationService.registerUser(signUpRequest);
    }
}
