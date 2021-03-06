package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.dto.request.TeamAnswerRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.ResponseBuilder;

import com.socialgame.alpha.dto.response.minigame.TeamAnswerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import com.socialgame.alpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;


    @Autowired
    public void setPlayerRepository (PlayerRepository playerRepository) { this.playerRepository = playerRepository;}

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) { this.teamRepository = teamRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) { this.gameRepository = gameRepository;}

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository;}


    @Override
    public ResponseEntity<?> togglePlayerColor(Long id, HttpServletRequest request)  {
        Color newColor = null;

        String username = request.getUserPrincipal().getName();
        Player playerJwt = userRepository.findByUsername(username)
                .orElseThrow(() -> new  EntityNotFoundException("User with: " + username + " does not exist."))
                .getPlayer();

        Player playerWeb = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player with ID: " + id + " does not exist."));

        if (!playerJwt.equals(playerWeb)) {
            throw new IllegalArgumentException("You may only change your own color");
        }

        for (int i = 0; i < Color.values().length - 1; i++) {
            if (playerWeb.getColor().equals(Color.values()[i])) {
                newColor = Color.values()[i + 1];
            }
        }

        playerWeb.setColor(newColor == null ? Color.RED : newColor);
        playerRepository.save(playerWeb);

        return ResponseEntity.ok(ResponseBuilder.playerResponse(playerWeb));
    }

    @Override
    public ResponseEntity<?> teamAnswer(HttpServletRequest httpRequest, TeamAnswerRequest answerRequest) {
        String username = httpRequest.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with: " + username + " does not exist."));

        String gameIdString = user.getGameIdString();
        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));

        if (!game.getStarted())  throw new IllegalArgumentException("Game has not yet started.");

        if (user.getTeam()== null) throw new NullPointerException("User doesn't have any team assigned, fatal error for game.");
        Team team = user.getTeam();

        if (!game.getCurrentCompetingTeams().contains(team)) throw new IllegalArgumentException("Team: " + team.getName() + " does not compete in this MiniGame.");

        if (team.getHasAnswered()) throw new IllegalArgumentException("Team: " + team.getName() + " has already answered.");

        switch (game.getCurrentMiniGame().getMiniGameType()) {
            case QUESTION:
                return answerQuestion(game,team, answerRequest);
            default:
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.addError("BAD_REQUEST", "Minigame has not yet deployed");
                return ResponseEntity.status(400).body(errorResponse);
        }
    }

    public ResponseEntity<?> answerQuestion(Game game, Team team, TeamAnswerRequest answerRequest) {
        Question question = (Question) game.getCurrentMiniGame();
        team.setHasAnswered(true);

        if (!question.getCorrectAnswer().equals(answerRequest.getAnswer())) {
            TeamAnswerResponse response = ResponseBuilder.teamAnswerResponse(team, question, answerRequest.getAnswer(), false);
            return ResponseEntity.ok(response);
        }

        team.setPoints(team.getPoints() + question.getPoints());
        teamRepository.save(team);

        if (team.getPoints() >= game.getScoreToWin()) {
            return ResponseEntity.ok("You have won the game!");
        }

        TeamAnswerResponse response = ResponseBuilder.teamAnswerResponse(team, question, answerRequest.getAnswer(), true);
        return ResponseEntity.ok(response);
    }
}
