package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.exception.PlayerNotFoundException;
import com.socialgame.alpha.payload.request.NewPlayerRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/player")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {this.playerService = playerService;}

    @GetMapping(path="/players")
    public ResponseEntity<?> findAllPlayers () {
        return playerService.findAllPlayers();
    }

    @GetMapping(path="/player/{id}")
    public ResponseEntity<?> findPlayerByID (@PathVariable("id") Long id) {
        return playerService.findPlayerByID(id);
    }

    @GetMapping(path="/player/toggle/{id}")
    public ResponseEntity<?> togglePlayerColor (@PathVariable("id") Long id)  {
        return playerService.togglePlayerColor(id);
    }

    @PostMapping(path ="/newplayer")
    public ResponseEntity<?> newPlayer(@Valid @RequestBody NewPlayerRequest newPlayerRequest) {
        return playerService.newPlayer(newPlayerRequest);
    }

    @PostMapping(path ="/join")
    public ResponseEntity<?> joinGame(@Valid @RequestBody NewPlayerRequest newPlayerRequest) {
        return playerService.newPlayer(newPlayerRequest);
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
