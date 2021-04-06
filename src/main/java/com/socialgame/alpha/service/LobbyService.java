package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.CreateGameRequest;
import org.springframework.http.ResponseEntity;

public interface LobbyService {
    ResponseEntity<?> createGame(CreateGameRequest createGameRequest);
    ResponseEntity<?> lobbyStatusUpdate(Long id);
    ResponseEntity<?> setGameType(Long id, String gameType);
    ResponseEntity<?> setPoints(Long id, int points);

}
