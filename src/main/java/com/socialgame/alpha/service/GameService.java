package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface GameService {

    public ResponseEntity<?> findAllGames();
    public ResponseEntity<?> findPlayersByGameId(Long id);
    public ResponseEntity<?> lobbyHeader(Long id);
}
