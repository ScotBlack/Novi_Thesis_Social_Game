package com.socialgame.alpha.service;

import com.socialgame.alpha.payload.request.TeamAnswerRequest;
import org.springframework.http.ResponseEntity;

public interface TeamService {

    ResponseEntity<?> teamAnswer(TeamAnswerRequest teamAnswerRequest);
}
