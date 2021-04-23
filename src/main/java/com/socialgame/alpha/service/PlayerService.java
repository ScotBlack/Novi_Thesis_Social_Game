package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.TeamAnswerRequest;
import org.springframework.http.ResponseEntity;

public interface PlayerService {

    ResponseEntity<?> togglePlayerColor(Long id);
    ResponseEntity<?> teamAnswer(TeamAnswerRequest teamAnswerRequest);

}
