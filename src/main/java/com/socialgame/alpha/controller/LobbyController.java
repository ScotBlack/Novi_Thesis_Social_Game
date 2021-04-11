package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.payload.request.CreateGameRequest;
import com.socialgame.alpha.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private LobbyService lobbyService;

    @Autowired
    private void setLobbyService(LobbyService lobbyService) {this.lobbyService = lobbyService;}

    @PostMapping("create")
    public ResponseEntity<?> createGame (@Valid @RequestBody CreateGameRequest createGameRequest) {
        return lobbyService.createGame(createGameRequest);
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<?> getPlayers(@PathVariable("id") Long id) {
        return lobbyService.getPlayers(id);
    }


    @GetMapping("/{id}/status")
    public ResponseEntity<?> lobbyStatusUpdate(@PathVariable("id") Long id) {
        return lobbyService.lobbyStatusUpdate(id);
    }

    @GetMapping(path="/{id}/setGameType/{gameType}")
    public ResponseEntity<?> setGameType(@PathVariable("id") Long id, @PathVariable("gameType") GameType gameType) {
        return lobbyService.setGameType(id, gameType);
    }

    @GetMapping(path="/{id}/setPoints/{points}")
    public ResponseEntity<?> setPoints(@PathVariable("id") Long id, @PathVariable("points")int points) {
        return lobbyService.setPoints(id, points);
    }

    @GetMapping(path="/{id}/start")
    public ResponseEntity<?> startGame(@PathVariable("id") Long id) {
        return lobbyService.startGame(id);
    }
}
