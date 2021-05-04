package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import com.socialgame.alpha.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path="/setGameType")
    public ResponseEntity<?> setGameType(@RequestBody SetGameTypeRequest setGameTypeRequest) {
        return hostService.setGameType(setGameTypeRequest);
    }

    @GetMapping(path="/setPoints/{points}")
    public ResponseEntity<?> setPoints(@PathVariable("id") Long id, @PathVariable("points")int points) {
        return hostService.setPoints(id, points);
    }

    @GetMapping(path="/{id}/start")
    public ResponseEntity<?> startGame(@PathVariable("id") Long id) {
        return hostService.startGame(id);
    }
}
