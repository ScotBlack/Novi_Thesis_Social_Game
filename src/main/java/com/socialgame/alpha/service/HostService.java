package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.enums.GameType;
import org.springframework.http.ResponseEntity;

public interface HostService {

    ResponseEntity<?> toggleOtherPlayerColor(Long id);
    ResponseEntity<?> setGameType(Long id, GameType gameType);
    ResponseEntity<?> setPoints(Long id, int points);
    ResponseEntity<?> startGame(Long id);
}
