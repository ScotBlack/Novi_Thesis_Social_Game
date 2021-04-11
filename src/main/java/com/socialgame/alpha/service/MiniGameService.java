package com.socialgame.alpha.service;

import org.springframework.http.ResponseEntity;

public interface MiniGameService {
    public ResponseEntity<?> nextMiniGame(Long id);
}
