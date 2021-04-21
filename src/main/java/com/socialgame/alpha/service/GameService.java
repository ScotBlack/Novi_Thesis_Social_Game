package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface GameService {

    ResponseEntity<?> findAllGames();
    ResponseEntity<?> getPlayers(Long id);
    ResponseEntity<?> lobbyStatusUpdate(Long id);
    ResponseEntity<?> getTeams(Long id);
    ResponseEntity<?> getScore(Long id);
}
