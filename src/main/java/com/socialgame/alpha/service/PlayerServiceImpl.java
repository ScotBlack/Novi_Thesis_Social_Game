package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.enums.Color;
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
        player.setColor(togglePlayerColor(player.getColor()));
        playerRepository.save(player);

        return ResponseEntity.ok(createResponseObject(player));
    }

    public Color togglePlayerColor(Color color) {
        Color[] colors = Color.values();

        for (int i = 0; i < colors.length; i++) {
            if (color.equals(colors[i]) && i < 7) {
                return colors[i + 1];
            }
        }
        return colors[0];
    }


    @Override
    public ResponseEntity<?> joinGame(NewPlayerRequest newPlayerRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        Player player = new Player();

        // check if game exist
        if (gameRepository.findById(newPlayerRequest.getGameId()).isEmpty()) {
            errorResponse.addError("404", "Game with ID: " + newPlayerRequest.getGameId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = gameRepository.findById(newPlayerRequest.getGameId()).get();

        // check if game has player with same name:
        if (playerRepository.findPlayerByNameAndGameId(game.getId(), newPlayerRequest.getName()) != null) {
            errorResponse.addError("409", "Name already exists in game.");
        }

        if(!newPlayerRequest.getPhone().equals("true")&&!newPlayerRequest.getPhone().equals("false")) {
            errorResponse.addError("406", "Phone must be either true or false");
        }

        if (errorResponse.getErrors().size() > 0) {
            return ResponseEntity.status(400).body(errorResponse);
        }

        player.setName(newPlayerRequest.getName());
        player.setColor(Color.values()[newPlayerColor(game)]);
        player.setPhone(newPlayerRequest.getPhone().equals("true"));
        player.setGame(game);
        game.addPlayer(player);

        playerRepository.save(player);
        gameRepository.save(game);

        return ResponseEntity.ok(createResponseObject(player));
    }

    public int newPlayerColor(Game game) {
        int c = 0;
        for (int i = 0; i < game.getPlayers().size(); i++) {
            c++;
            if (c == 8) {
                c = 0;
            }
        }
        return c;
    }


    public PlayerResponse createResponseObject (Player player) {
        return (
            new PlayerResponse (
                player.getId(),
                player.getName(),
                player.getColor().toString(),
                player.getPhone(),
                player.getGame().getId()
            )
        );
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
