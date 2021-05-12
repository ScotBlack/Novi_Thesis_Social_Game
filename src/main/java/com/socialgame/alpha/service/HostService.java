package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.SetGamePointsRequest;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface HostService {
    ResponseEntity<?> setGameType(SetGameTypeRequest setGameTypeRequest);
    ResponseEntity<?> setPoints(SetGamePointsRequest setGamePointsRequest);
    ResponseEntity<?> startGame(HttpServletRequest request);
}
