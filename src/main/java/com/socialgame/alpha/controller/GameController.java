package com.socialgame.alpha.controller;

import com.socialgame.alpha.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) { this.gameService = gameService;}

    @GetMapping(path="/games")
    public ResponseEntity<?> findAllGames() {
        return gameService.findAllGames();
    }

    @GetMapping("/lobbyStatus/{id}")
    public ResponseEntity<?> lobbyStatusUpdate(@PathVariable("id") Long id) {
        return gameService.lobbyStatusUpdate(id);
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<?> getPlayers(@PathVariable("id") Long id) {
        return gameService.getPlayers(id);
    }

    @GetMapping(path="/{id}/teams")
    public ResponseEntity<?> getTeams(@PathVariable("id") Long id) {
        return gameService.getTeams(id);
    }

    // redundant, same as getTeams()
    @GetMapping(path="/{id}/score")
    public ResponseEntity<?> getScore(@PathVariable("id") Long id) {
        return gameService.getScore(id);
    }
}


