package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import org.springframework.http.ResponseEntity;

public interface StartService {
    ResponseEntity<?>  createGame (CreateGameRequest createGameRequest);
    ResponseEntity<?> joinGame (JoinGameRequest joinGameRequest);

}
