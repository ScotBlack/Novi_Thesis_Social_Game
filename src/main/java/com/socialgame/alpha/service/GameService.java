package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface GameService {


    ResponseEntity<?> findPlayerByID(Long id);
    ResponseEntity<?> lobbyStatusUpdate(HttpServletRequest request);
    ResponseEntity<?> getPlayers(String  gameIdString);
    ResponseEntity<?> getScore(String  gameIdString);
    ResponseEntity<?> nextMiniGame(String  gameIdString);
}
