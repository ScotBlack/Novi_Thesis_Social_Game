package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.TeamScoreResponse;
import com.socialgame.alpha.payload.response.PlayerResponse;
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

        Set<Color> teams = optionalGame.get().getTeams();

        return ResponseEntity.ok(teams); // needs response object
    }

    @Override
    public ResponseEntity<?> getPlayers(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        List<Player> players = playerRepository.findPlayersByGameId(id);

        return ResponseEntity.ok(createResponseObject(players));
    }

    // getPlayersFromTeam(Long Id, Color color/team)

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

        Set<Color> teams = game.getTeams();

        for (Color color : teams) {
            TeamScoreResponse team = new TeamScoreResponse(color.toString());


            for (Player p : game.getPlayers()) {
                if (p.getColor().equals(color)) {
                    team.addMembers(p.getName());
                }
                if (game.getCaptains().contains(p.getId())) {
                    team.setPoints(p.getPoints());
                }
            }
            highScores.add(team);
        }
        return highScores;
    }

    public PlayerResponse createResponseObject (Player player) {

        PlayerResponse playerResponse =
            new PlayerResponse (
                player.getId(),
                player.getName(),
                player.getColor().toString(),
                player.getPhone(),
                player.getGame().getId()
            );

        return playerResponse;
    }

    public Set<PlayerResponse> createResponseObject (List<Player> players) {
        Set<PlayerResponse> playerResponseList = new HashSet<>();

        for (Player player : players) {
            PlayerResponse playerResponse = createResponseObject(player);
            playerResponseList.add(playerResponse);
        }

        return playerResponseList;
    }



}
