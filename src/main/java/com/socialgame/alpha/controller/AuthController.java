package com.socialgame.alpha.controller;

import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.dto.request.LoginRequest;
import com.socialgame.alpha.dto.request.SignupRequest;
import com.socialgame.alpha.dto.response.JwtResponse;
import com.socialgame.alpha.dto.response.MessageResponse;
import com.socialgame.alpha.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Uitleg over CrossOrigin en CORS:
 * https://medium.com/@baphemot/understanding-cors-18ad6b478e2b
 *
 * Gebruik in Spring-boot (op controller en globally)
 * https://www.tutorialspoint.com/spring_boot/spring_boot_cors_support.htm
 *
 * Zoals je hieronder ziet, kun je ook op klasse-niveau een adres configureren. Iaw alle methodes hieronder, hebben
 * /api/auth voor de link staan.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authorizationService.authenticateUser(loginRequest);
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
//        return authorizationService.registerUser(signUpRequest);
//    }

    @PostMapping("/creategame")
    public ResponseEntity<?> createGame(@RequestBody CreateGameRequest createGameRequest) {
        return authorizationService.createGame(createGameRequest);
    }

    @PostMapping("/joingame")
    public ResponseEntity<?> joinGame(@RequestBody JoinGameRequest joinGameRequest) {
        return authorizationService.joinGame(joinGameRequest);
    }

}