package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import com.socialgame.alpha.dto.request.SetPointsRequest;
import com.socialgame.alpha.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<?> setGameType(@Valid  @RequestBody SetGameTypeRequest setGameTypeRequest) {
        return hostService.setGameType(setGameTypeRequest);
    }

    @PostMapping(path="/setPoints")
    public ResponseEntity<?> setPoints(@Valid @RequestBody SetPointsRequest setPointsRequest) {
        return hostService.setPoints(setPointsRequest);
    }

    @GetMapping(path="/{id}/start")
    public ResponseEntity<?> startGame(@PathVariable("id") Long id) {
        return hostService.startGame(id);
    }
}
