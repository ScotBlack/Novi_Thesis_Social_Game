package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import org.springframework.http.ResponseEntity;

public interface HostService {

    ResponseEntity<?> toggleOtherPlayerColor(Long id);
    ResponseEntity<?> setGameType(SetGameTypeRequest setGameTypeRequest);
    ResponseEntity<?> setPoints(Long id, int points);
    ResponseEntity<?> startGame(Long id);
}
