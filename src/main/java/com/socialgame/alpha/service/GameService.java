package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface GameService {

    // whole database
    ResponseEntity<?> findAllGames();
    ResponseEntity<?> findAllPlayers();
    ResponseEntity<?> findPlayerByID(Long id);

    // related to a particular game
    ResponseEntity<?> lobbyStatusUpdate(HttpServletRequest request);
    ResponseEntity<?> getPlayers(String  gameIdString);
    ResponseEntity<?> getTeams(Long id);
    ResponseEntity<?> getScore(Long id);
    ResponseEntity<?> nextMiniGame(Long id);
}
