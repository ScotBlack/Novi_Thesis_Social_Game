package com.socialgame.alpha.controller;

import com.socialgame.alpha.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class GameController {

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) { this.gameService = gameService;}

//    @GetMapping(path="/games")
//    public ResponseEntity<?> findAllGames() {
//        return gameService.findAllGames();
//    }
//
//    @GetMapping(path="/game/{id}/players")
//    public ResponseEntity<?> findPlayersByGameId(@PathVariable("id") Long id) {
//        return gameService.findPlayersByGameId(id);
//    }
//
//    @GetMapping(path="/game/{id}/lobbyHeader")
//    public ResponseEntity<?> lobbyHeader(@PathVariable("id")Long id) {
//        return gameService.lobbyHeader(id);
//    }
//
//    @GetMapping("/game/{id}/{gameType}")
//    public ResponseEntity<?> setGameType(@PathVariable("id")Long id,
//                                         @PathVariable("gameType")String gameType) {
//        return gameService.setGameType(id, gameType);
//    }
//
//    @GetMapping ("game/{id}/start")
//    public ResponseEntity<?> start (@PathVariable("id")Long id) {
//        return gameService.start(id);
//    }


}
