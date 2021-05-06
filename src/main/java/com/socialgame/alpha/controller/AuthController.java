package com.socialgame.alpha.controller;

import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.service.AuthorizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthorizationServiceImpl authorizationService;

    @PostMapping("/creategame")
    public ResponseEntity<?> createGame(@Valid @RequestBody CreateGameRequest createGameRequest) {
        return authorizationService.createGame(createGameRequest);
    }

    @PostMapping("/joingame")
    public ResponseEntity<?> joinGame(@RequestBody JoinGameRequest joinGameRequest) {
        return authorizationService.joinGame(joinGameRequest);
    }

    @PostMapping("/rejoin")
    public ResponseEntity<?> rejoinGame(@RequestBody JoinGameRequest joinGameRequest) {
        return authorizationService.rejoin(joinGameRequest);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}