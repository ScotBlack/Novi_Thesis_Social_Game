package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface PlayerService {

    ResponseEntity<?> findAllPlayers();
    ResponseEntity<?> findPlayerByID(Long id);
}
