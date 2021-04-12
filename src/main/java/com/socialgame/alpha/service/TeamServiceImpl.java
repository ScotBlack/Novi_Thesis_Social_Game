package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Team;

import com.socialgame.alpha.payload.request.TeamAnswerRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;

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

//       if (game.g)


       Optional<Team> optionalTeam = teamRepository.findById(teamAnswerRequest.getTeamId());

       if (optionalTeam.isEmpty()) {
           errorResponse.addError("404", "Team with ID: " + teamAnswerRequest.getTeamId() + " does not exist.");
           return ResponseEntity.status(404).body(errorResponse);
       }

       Team team = optionalTeam.get();



//       if (!game.getCaptains().contains(player.getId())) {
//           errorResponse.addError("403", "Player with ID: " + teamAnswerRequest.getTeamId() + " is not competing in this Mini Game.");
//           return ResponseEntity.status(403).body(errorResponse);
//       }
//
//       Boolean answer;
//
//       switch (game.getCurrentMiniGame().getMiniGameType()) {
//           case QUESTION:
//               Question question = (Question) game.getCurrentMiniGame();
//               if (question.getCorrectAnswer().equals(teamAnswerRequest.getAnswer())) {
//                   player.setPoints(player.getPoints() + question.getPoints());
//                   answer = true;
//               } else {
//                   answer = false;
//               }
//               break;
//           case DARE:
//           case BEST_ANSWER:
//           case RANKING:
//           case GUESS_WORD:
//               errorResponse.addError("403", "This Mini Game Type has not yet been deployed.");
//               break;
//       }




       return ResponseEntity.status(403).body(errorResponse);
    }


}
