package com.socialgame.alpha.controller;

import com.socialgame.alpha.payload.request.TeamAnswerRequest;
import com.socialgame.alpha.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TeamController {

    private TeamService teamService;

    @Autowired
    public void setTeamService (TeamService teamService) { this.teamService = teamService;}

    @PostMapping(path="/answer")
    public ResponseEntity<?> teamAnswer(@Valid @RequestBody TeamAnswerRequest teamAnswerRequest) {
        return teamService.teamAnswer(teamAnswerRequest);
    }
}
