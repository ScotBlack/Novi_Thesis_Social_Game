package com.socialgame.alpha.controller;

import com.socialgame.alpha.payload.request.CreateGameRequest;
import com.socialgame.alpha.payload.request.NewPlayerRequest;
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
}
