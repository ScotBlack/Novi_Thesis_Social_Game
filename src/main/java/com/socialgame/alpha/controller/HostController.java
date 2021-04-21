package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/host")
public class HostController {

    private HostService hostService;

    @Autowired
    public void setHostService (HostService hostService) {this.hostService = hostService;}

    @GetMapping(path="/toggle/{id}")
    public ResponseEntity<?> toggleOtherPlayerColor (@PathVariable("id") Long id)  {
        return hostService.toggleOtherPlayerColor(id);
    }

    @GetMapping(path="/{id}/setGameType/{gameType}")
    public ResponseEntity<?> setGameType(@PathVariable("id") Long id, @PathVariable("gameType") GameType gameType) {
        return hostService.setGameType(id, gameType);
    }

    @GetMapping(path="/{id}/setPoints/{points}")
    public ResponseEntity<?> setPoints(@PathVariable("id") Long id, @PathVariable("points")int points) {
        return hostService.setPoints(id, points);
    }

    @GetMapping(path="/{id}/start")
    public ResponseEntity<?> startGame(@PathVariable("id") Long id) {
        return hostService.startGame(id);
    }
}
