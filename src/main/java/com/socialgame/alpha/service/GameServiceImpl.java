package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.PlayerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {


    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Override
    public ResponseEntity<?> findAllGames() {
        return ResponseEntity.ok(gameRepository.findAll());
    }

    @Override
    public ResponseEntity<?> findPlayersByGameId(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        List<Player> players = playerRepository.findPlayersByGameId(id);

        return ResponseEntity.ok(createResponseObject(players));
    }

    @Override
    public ResponseEntity<?> verifyPlayers(Long id) {
        return ResponseEntity.ok(createResponseObject());
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
