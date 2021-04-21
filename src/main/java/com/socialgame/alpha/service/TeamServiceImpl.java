package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Team;

import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.payload.request.TeamAnswerRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;

import com.socialgame.alpha.payload.response.minigame.QuestionResponse;
import com.socialgame.alpha.payload.response.minigame.TeamAnswerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private GameRepository gameRepository;

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) { this.teamRepository = teamRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) { this.gameRepository = gameRepository;}


    public ResponseEntity<?> teamAnswer(TeamAnswerRequest teamAnswerRequest) {
        ErrorResponse errorResponse = new ErrorResponse();

       Optional<Game> optionalGame = gameRepository.findById(teamAnswerRequest.getGameId());

       if (optionalGame.isEmpty()) {
           errorResponse.addError("404", "Game with ID: " + teamAnswerRequest.getGameId() + " does not exist.");
           return ResponseEntity.status(404).body(errorResponse);
       }

       Game game = optionalGame.get();

        Optional<Team> optionalTeam = teamRepository.findById(teamAnswerRequest.getTeamId());

        if (optionalTeam.isEmpty()) {
            errorResponse.addError("404", "Team with ID: " + teamAnswerRequest.getTeamId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Team team = optionalTeam.get();

        // check if team is in game??

       if (!game.getCurrentCompetingTeams().contains(team)) {
           errorResponse.addError("403", "Team with ID: " + teamAnswerRequest.getTeamId() + " does not compete in this Mini Game.");
       }

       // check if team already answer the question, so they cant get double triple score

       switch (game.getCurrentMiniGame().getMiniGameType()) {
           case QUESTION:
               Question question = (Question) game.getCurrentMiniGame();
               if (question.getCorrectAnswer().equals(teamAnswerRequest.getAnswer())) {
                   team.setPoints(team.getPoints() + question.getPoints());
                   teamRepository.save(team);
                   return ResponseEntity.ok(createResponseObject(team, question, teamAnswerRequest.getAnswer(),true));
               } else {
                   return ResponseEntity.ok(createResponseObject(team, question,teamAnswerRequest.getAnswer(), false));
               }
           case DARE:
           case BEST_ANSWER:
           case RANKING:
           case GUESS_WORD:
               errorResponse.addError("403", "This Mini Game Type has not yet been deployed.");
               break;
       }

       return ResponseEntity.status(403).body(errorResponse);
    }

    public TeamAnswerResponse createResponseObject (Team team, MiniGame minigame, String answer, Boolean answerCorrect) {

        TeamAnswerResponse response =
            new TeamAnswerResponse (
                team.getId(),
                minigame.getQuestion(),
                answer,
                answerCorrect,
                minigame.getPoints(),
                team.getPoints()
            );

        return response;
    }
}
