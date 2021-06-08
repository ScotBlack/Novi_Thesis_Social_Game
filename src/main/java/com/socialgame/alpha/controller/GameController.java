package com.socialgame.alpha.controller;

import com.socialgame.alpha.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/game")
public class GameController {

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) { this.gameService = gameService;}


    @GetMapping(path="/player/{id}")
    public ResponseEntity<?> findPlayerByID (@PathVariable("id") Long id) {
        return gameService.findPlayerByID(id);
    }

    @GetMapping("/{gameIdString}/players")
    public ResponseEntity<?> getPlayers(@PathVariable("gameIdString") String gameIdString) {
        return gameService.getPlayers(gameIdString);
    }

    @GetMapping("/lobbyStatus")
    public ResponseEntity<?> lobbyStatusUpdate(HttpServletRequest request) {
        return gameService.lobbyStatusUpdate(request);
    }

    @GetMapping(path="/{gameIdString}/score")
    public ResponseEntity<?> getScore(@PathVariable("gameIdString") String gameIdString) {
        return gameService.getScore(gameIdString);
    }

    @GetMapping(path="/{id}/nextMiniGame")
    public ResponseEntity<?> nextMiniGame(@PathVariable("id") String gameIdString) {
        return gameService.nextMiniGame(gameIdString);
    }
}


