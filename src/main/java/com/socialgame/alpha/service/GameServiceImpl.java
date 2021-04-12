package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.TeamScoreResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.minigame.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {


    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {this.questionRepository = questionRepository;}

    @Override
    public ResponseEntity<?> findAllGames() {
        return ResponseEntity.ok(gameRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getTeams(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Set<Team> teams = optionalGame.get().getTeams();

        return ResponseEntity.ok(teams); // needs response object
    }

    @Override
    public ResponseEntity<?> getScore(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

        if (!game.getStarted()) {
            errorResponse.addError("403" , "Game with ID: " + id + " has not yet started.");
            return ResponseEntity.status(403).body(errorResponse);
        }

        return ResponseEntity.ok(createResponseObject(game));
    }


    public Set<TeamScoreResponse> createResponseObject (Game game) {
        Set<TeamScoreResponse> highScores = new HashSet<>();

        Set<Team> teams = game.getTeams();

        for (Team team: teams) {

            TeamScoreResponse teamResponse =
                new TeamScoreResponse(
                    team.getName().toString(),
                    team.getPoints(),
                    team.getPlayers().keySet()
                );

            highScores.add(teamResponse);
        }
        return highScores;
    }







}
