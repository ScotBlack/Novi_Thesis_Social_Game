package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.request.JoinGameRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.PlayerObjectResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;

    @Autowired
    public void setPlayerRepository (PlayerRepository playerRepository) { this.playerRepository = playerRepository;}

    @Autowired
    public void setLobbyRepository (LobbyRepository lobbyRepository) { this.lobbyRepository = lobbyRepository;}

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
    public ResponseEntity<?> toggleColor(Long id)  {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if (optionalPlayer.isEmpty()) {
            errorResponse.addError("404" , "Player with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Player player = optionalPlayer.get();
        Color[] colors = Color.values();

        Color currentColor = player.getColor();
        Color newColor = Color.RED;

        for (int i = 0; i < colors.length; i++) {
            if (currentColor.equals(colors[i]) && i < colors.length - 1) {
                newColor = colors[i + 1];
            }
        }

        player.setColor(newColor);
        playerRepository.save(player);

        return ResponseEntity.ok(createResponseObject(player));
    }

    @Override
    public ResponseEntity<?> joinLobby(JoinGameRequest newPlayerRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        Player player = new Player();


        Optional<Lobby> optionalLobby = lobbyRepository.findById(newPlayerRequest.getLobbyId());
        // check if game exist
        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404", "Lobby with ID: " + newPlayerRequest.getLobbyId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();

        //check if game has started yet or not

        // check if game has player with same name:
        if (playerRepository.findPlayerByNameAndLobbyId(lobby.getId(), newPlayerRequest.getName()) != null) {
            errorResponse.addError("409", "Name already exists in game.");
        }

        if(!newPlayerRequest.getPhone().equals("true")&&!newPlayerRequest.getPhone().equals("false")) {
            errorResponse.addError("406", "Phone must be either true or false");
        }

        if (errorResponse.getErrors().size() > 0) {
            return ResponseEntity.status(400).body(errorResponse);
        }

        int c = 0;
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            c++;
            if (c == 8) {
                c = 0;
            }
        }

        player.setName(newPlayerRequest.getName());
        player.setColor(Color.values()[c]);
        player.setPhone(newPlayerRequest.getPhone().equals("true"));
        player.setLobby(lobby);
        lobby.getPlayers().add(player);

        playerRepository.save(player);
        lobbyRepository.save(lobby);

        return ResponseEntity.ok(createResponseObject(player));
    }


    public PlayerObjectResponse createResponseObject (Player player) {
        return (
            new PlayerObjectResponse(
                player.getId(),
                player.getName(),
                player.getColor().toString(),
                player.getPhone(),
                player.getLobby().getId()
            )
        );
    }

    public Set<PlayerObjectResponse> createResponseObject (List<Player> players) {
        Set<PlayerObjectResponse> playerObjectResponseList = new HashSet<>();

        for (Player player : players) {
            PlayerObjectResponse playerObjectResponse = createResponseObject(player);
            playerObjectResponseList.add(playerObjectResponse);
        }

        return playerObjectResponseList;
    }
}
