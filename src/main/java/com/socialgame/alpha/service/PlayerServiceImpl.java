package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.EColors;
import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.request.NewPlayerRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.PlayerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService{

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) { this.playerRepository = playerRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) { this.gameRepository = gameRepository;}


    @Override
    public ResponseEntity<?> findAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return ResponseEntity.ok(createResponseObject(players));
    }


    @Override
    public ResponseEntity<?> findPlayerByID(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if (optionalPlayer.isEmpty()) {
            errorResponse.addError("404" , "Player with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Player player = optionalPlayer.get();
        return ResponseEntity.ok(createResponseObject(player));
    }


    @Override
    public ResponseEntity<?> togglePlayerColor(Long id)  {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if (optionalPlayer.isEmpty()) {
            errorResponse.addError("404" , "Player with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Player player = optionalPlayer.get();
        player.setColor(EColors.toggleColor(player.getColor()));
        playerRepository.save(player);

        return ResponseEntity.ok(createResponseObject(player));
    }


    @Override
    public ResponseEntity<?> newPlayer(NewPlayerRequest newPlayerRequest) {
        ErrorResponse errorResponse = new ErrorResponse();

        // player
        Player player = new Player();
        player.setName(newPlayerRequest.getName());
        player.setColor(EColors.newPlayerColor(2));

        if (newPlayerRequest.getPhone().equals("true")) {
            player.setPhone(true);
        } else if (newPlayerRequest.getPhone().equals("false")) {
            player.setPhone(false);
        } else {
            errorResponse.addError("newPlayerRequest.phone", newPlayerRequest.getPhone() + "must be  true/false" );

        }

        // game
        if (!gameExists(newPlayerRequest.getGameId())) {
            errorResponse.addError("404", "Player with ID: " + newPlayerRequest.getGameId() + " does not exist.");
        } else {
            Game game = gameRepository.findById(newPlayerRequest.getGameId()).get();
            player.setGame(game);
            game.addPlayer(player);
        }

        playerRepository.save(player);

        return ResponseEntity.ok(createResponseObject(player));
    }


    public boolean gameExists (Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isEmpty()) {
            return false;
        }
        return true;
    }


    public PlayerResponse createResponseObject (Player player) {
        PlayerResponse playerResponse = new PlayerResponse (player.getId(), player.getName(), player.getColor(), player.getPhone(), player.getGame().getId());

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
