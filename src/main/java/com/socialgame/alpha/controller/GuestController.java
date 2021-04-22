package com.socialgame.alpha.controller;

import com.socialgame.alpha.payload.request.CreateGameRequest;
import com.socialgame.alpha.payload.request.JoinGameRequest;
import com.socialgame.alpha.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/guest")
public class GuestController {

    private GuestService guestService;

    @Autowired
    public void setGuestService(GuestService guestService) {this.guestService = guestService;}

    @PostMapping("/createGame")
    public ResponseEntity<?> createGame (@Valid @RequestBody CreateGameRequest createGameRequest) {
        return guestService.createGame(createGameRequest);
    }

    @PostMapping(path ="/joinGame")
    public ResponseEntity<?> joinGame(@Valid @RequestBody JoinGameRequest joinGameRequest) {
        return guestService.joinGame(joinGameRequest);
    }


}
