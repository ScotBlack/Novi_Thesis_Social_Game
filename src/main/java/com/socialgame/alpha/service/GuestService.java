package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.CreateGameRequest;
import com.socialgame.alpha.payload.request.JoinGameRequest;
import org.springframework.http.ResponseEntity;

public interface GuestService {
    ResponseEntity<?> createGame (CreateGameRequest createGameRequest);
    ResponseEntity<?> joinGame(JoinGameRequest joinGameRequest);
}
