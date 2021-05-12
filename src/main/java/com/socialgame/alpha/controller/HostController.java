package com.socialgame.alpha.controller;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.JwtRequest;
import com.socialgame.alpha.dto.request.SetGamePointsRequest;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import com.socialgame.alpha.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/host")
public class HostController {

    private HostService hostService;

    @Autowired
    public void setHostService (HostService hostService) {this.hostService = hostService;}

    @PostMapping(path="/setGameType")
    public ResponseEntity<?> setGameType(@Valid  @RequestBody SetGameTypeRequest setGameTypeRequest) {
        return hostService.setGameType(setGameTypeRequest);
    }

    @PostMapping(path="/setPoints")
    public ResponseEntity<?> setPoints(@Valid @RequestBody SetGamePointsRequest setGamePointsRequest) {
        return hostService.setPoints(setGamePointsRequest);
    }

    @GetMapping(path="/start")
    public ResponseEntity<?> startGame(HttpServletRequest request) {
        return hostService.startGame(request);
    }
}
