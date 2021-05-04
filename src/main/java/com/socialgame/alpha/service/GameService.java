package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface GameService {

    // whole database
    ResponseEntity<?> findAllGames();
    ResponseEntity<?> findAllPlayers();
    ResponseEntity<?> findPlayerByID(Long id);

    // related to a particular game
    ResponseEntity<?> lobbyStatusUpdate(String gameIdString);
    ResponseEntity<?> getPlayers(String  gameIdString);
    ResponseEntity<?> getTeams(Long id);
    ResponseEntity<?> getScore(Long id);
    ResponseEntity<?> nextMiniGame(Long id);
}
