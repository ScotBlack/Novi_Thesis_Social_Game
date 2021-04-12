package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.NewPlayerRequest;
import org.springframework.http.ResponseEntity;

public interface PlayerService {

    ResponseEntity<?> findAllPlayers();
    ResponseEntity<?> findPlayerByID(Long id);

    ResponseEntity<?> joinLobby(NewPlayerRequest newPlayerRequest);
    ResponseEntity<?> toggleColor(Long id);
//    ResponseEntity<?> playerAnswer(PlayerAnswerRequest playerAnswerRequest);


//    PlayerResponse createResponseObject (Player player);
//    Set<PlayerResponse> createResponseObject (List<Player> players);


}
