package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import org.springframework.http.ResponseEntity;

public interface AuthorizationService {
    ResponseEntity<?> createGame (CreateGameRequest createGameRequest);
    public ResponseEntity<?> joinGame (JoinGameRequest joinGameRequest);
    public ResponseEntity<?> rejoin (JoinGameRequest joinGameRequest);
}
