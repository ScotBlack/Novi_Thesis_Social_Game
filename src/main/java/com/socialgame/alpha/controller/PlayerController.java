package com.socialgame.alpha.controller;

import com.socialgame.alpha.payload.request.JoinGameRequest;
import com.socialgame.alpha.payload.request.TeamAnswerRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {this.playerService = playerService;}

    // need to add something that also checks gameid
    @GetMapping(path="/toggle/{id}")
    public ResponseEntity<?> togglePlayerColor (@PathVariable("id") Long id)  {
        return playerService.togglePlayerColor(id);
    }

    @PostMapping(path="/answer")
    public ResponseEntity<?> teamAnswer(@Valid @RequestBody TeamAnswerRequest teamAnswerRequest) {
        return playerService.teamAnswer(teamAnswerRequest);
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
