package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.exception.PlayerNotFoundException;
import com.socialgame.alpha.payload.request.NewPlayerRequest;
import com.socialgame.alpha.payload.request.PlayerAnswerRequest;
import com.socialgame.alpha.payload.response.PlayerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface PlayerService {

    ResponseEntity<?> findAllPlayers();
    ResponseEntity<?> findPlayerByID(Long id);

    ResponseEntity<?> joinGame(NewPlayerRequest newPlayerRequest);
    ResponseEntity<?> toggleColor(Long id);
//    ResponseEntity<?> playerAnswer(PlayerAnswerRequest playerAnswerRequest);


//    PlayerResponse createResponseObject (Player player);
//    Set<PlayerResponse> createResponseObject (List<Player> players);


}
