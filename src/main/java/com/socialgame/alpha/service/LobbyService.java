package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.payload.request.CreateGameRequest;
import org.springframework.http.ResponseEntity;

public interface LobbyService {
//    ResponseEntity<?> createGame(CreateGameRequest createGameRequest);
    ResponseEntity<?> getPlayers(Long id);
    ResponseEntity<?> lobbyStatusUpdate(Long id);
//    ResponseEntity<?> setGameType(Long id, GameType gameType);
//    ResponseEntity<?> setPoints(Long id, int points);
//    ResponseEntity<?> startGame(Long id);

}
