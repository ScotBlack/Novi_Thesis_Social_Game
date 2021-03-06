package com.socialgame.alpha.controller;

import com.socialgame.alpha.dto.request.TeamAnswerRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {this.playerService = playerService;}

    @PostMapping(path="/answer")
    public ResponseEntity<?> teamAnswer(HttpServletRequest httpRequest, @Valid @RequestBody TeamAnswerRequest answerRequest) {
        return playerService.teamAnswer(httpRequest, answerRequest);
    }

    @GetMapping(path="/toggle/{id}")
    public ResponseEntity<?> togglePlayerColor (@PathVariable("id") Long id, HttpServletRequest request)  {
        return playerService.togglePlayerColor(id, request);
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
