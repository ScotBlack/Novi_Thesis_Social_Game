package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.CreateGameRequest;
import org.springframework.http.ResponseEntity;

public interface LobbyService {
    public ResponseEntity<?> createGame(CreateGameRequest createGameRequest);
}
