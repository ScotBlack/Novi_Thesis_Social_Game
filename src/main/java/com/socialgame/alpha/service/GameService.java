package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface GameService {
    public ResponseEntity<?> findAllGames();
    public ResponseEntity<?> getTeams(Long id);
    public ResponseEntity<?> getScore(Long id);
}
