package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.TeamAnswerRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface PlayerService {

    ResponseEntity<?> togglePlayerColor(Long id, HttpServletRequest request);
    ResponseEntity<?> teamAnswer(TeamAnswerRequest teamAnswerRequest);

}
