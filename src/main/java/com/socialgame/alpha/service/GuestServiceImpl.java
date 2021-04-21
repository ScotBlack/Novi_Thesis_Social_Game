package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.CreateGameRequest;
import com.socialgame.alpha.payload.request.JoinGameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements GuestService {

    public ResponseEntity<?> createGame (CreateGameRequest createGameRequest) {
        return ResponseEntity.ok("Hola createGame");
    }

    public ResponseEntity<?> joinGame(JoinGameRequest joinGameRequest) {
        return ResponseEntity.ok("Hola createGame");
    }
}
