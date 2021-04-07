package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface GameService {

    public ResponseEntity<?> getTeams(Long id);
//    public ResponseEntity<?> findAllGames();
//    public ResponseEntity<?> findPlayersByGameId(Long id);
//    public ResponseEntity<?> lobbyHeader(Long id);
//    public ResponseEntity<?> start(Long id);
//    public ResponseEntity<?> setGameType(Long id, String gameType);
}
