package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.JoinGameRequest;
import com.socialgame.alpha.payload.request.TeamAnswerRequest;
import org.springframework.http.ResponseEntity;

public interface PlayerService {

    ResponseEntity<?> togglePlayerColor(Long id);
    ResponseEntity<?> teamAnswer(TeamAnswerRequest teamAnswerRequest);

}
