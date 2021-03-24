package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.NewPlayerRequest;
import org.springframework.http.ResponseEntity;

public interface PlayerService {

    ResponseEntity<?> findAllPlayers();
    ResponseEntity<?> findPlayerByID(Long id);
    ResponseEntity<?> newPlayer(NewPlayerRequest newPlayerRequest);
}
