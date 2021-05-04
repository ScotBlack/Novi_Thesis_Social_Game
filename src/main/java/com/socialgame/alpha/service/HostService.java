package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.SetGamePointsRequest;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import org.springframework.http.ResponseEntity;

public interface HostService {
    ResponseEntity<?> setGameType(SetGameTypeRequest setGameTypeRequest);
    ResponseEntity<?> setPoints(SetGamePointsRequest setGamePointsRequest);
    ResponseEntity<?> startGame(String gameIdString);
}
