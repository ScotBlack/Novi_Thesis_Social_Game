package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    private PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}


    @GetMapping (path="/players")
    public ResponseEntity<?> printPlayers () {
        List<Player> players = playerRepository.findAll();

        return new ResponseEntity<> (players, HttpStatus.OK);
    }
}
