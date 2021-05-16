package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import org.springframework.http.ResponseEntity;

public interface StartService {
    String createGame (CreateGameRequest createGameRequest);

    ResponseEntity<?> authenticateUser(String username, String password);
    ResponseEntity<?> joinGame (JoinGameRequest joinGameRequest);
    public ResponseEntity<?> rejoin (JoinGameRequest joinGameRequest);
}
