package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.TeamResponse;
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

        Game game = optionalGame.get();
        Set<Team> teams = game.getTeams();

        return ResponseEntity.ok(createResponseObject(teams)); // needs response object
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

        return ResponseEntity.ok(createResponseObject(game.getTeams()));
    }


    public Set<TeamResponse> createResponseObject (Set<Team> teams) {
        Set<TeamResponse> teamsRes = new HashSet<>();

        for (Team team : teams) {
            TeamResponse response =
                    new TeamResponse(
                            team.getId(),
                            team.getGame().getId(),
                            team.getName().toString(),
                            team.getPlayers().keySet(),
                            team.getPoints()
                    );
            teamsRes.add(response);
        }

        return teamsRes;
    }







}
