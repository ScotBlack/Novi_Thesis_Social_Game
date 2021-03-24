package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/test")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {this.playerService = playerService;}

    @GetMapping (path="/players")
    public ResponseEntity<?> findAllPlayers () {
        return playerService.findAllPlayers();
    }

    @GetMapping (path="player/{id}")
    public ResponseEntity<?> findPlayerByID (@PathVariable("id") Long id) {
        return playerService.findPlayerByID(id);
    }

}
