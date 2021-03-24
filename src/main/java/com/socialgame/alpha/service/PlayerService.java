package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface PlayerService {

    ResponseEntity<?> printAllPlayers();
    ResponseEntity<?> findPlayerByID(Long id);
}
