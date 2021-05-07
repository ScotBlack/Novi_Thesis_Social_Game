package com.socialgame.alpha.controller;

import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.service.StartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/start")
public class StartController {

    @Autowired
    StartServiceImpl startService;

    @PostMapping("/creategame")
    public ResponseEntity<?> createGame(@Valid @RequestBody CreateGameRequest createGameRequest) {
        return startService.createGame(createGameRequest);
    }

    @PostMapping("/joingame")
    public ResponseEntity<?> joinGame(@RequestBody JoinGameRequest joinGameRequest) {
        return startService.joinGame(joinGameRequest);
    }

    @PostMapping("/rejoin")
    public ResponseEntity<?> rejoinGame(@RequestBody JoinGameRequest joinGameRequest) {
        return startService.rejoin(joinGameRequest);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorType = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.addError(errorType, errorMessage);});

        return ResponseEntity.status(400).body(errorResponse);
    }
}