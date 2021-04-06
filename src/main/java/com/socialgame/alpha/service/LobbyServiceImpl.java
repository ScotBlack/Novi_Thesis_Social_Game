package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.CreateGameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LobbyServiceImpl implements LobbyService {

    @Override
    public ResponseEntity<?> createGame(CreateGameRequest createGameRequest) {


        return ResponseEntity.ok("Hola muchachos");
    }
}
