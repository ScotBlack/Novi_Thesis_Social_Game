package com.socialgame.alpha.controller;

import com.socialgame.alpha.dto.request.SettingRequest;
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

    @PostMapping(path="/gamesetting")
    public ResponseEntity<?> setGameSetting(@Valid  @RequestBody SettingRequest request) {
        return hostService.setGameSetting(request);
    }

    @GetMapping(path="/start")
    public ResponseEntity<?> startGame(HttpServletRequest request) {
        return hostService.startGame(request);
    }
}
