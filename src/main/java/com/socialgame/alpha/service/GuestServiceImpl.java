package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.LobbyResponse;
import com.socialgame.alpha.dto.response.PlayerObjectResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    @Autowired
    public void setLobbyRepository(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


//    @Override
    public ResponseEntity<?> createGame(CreateGameRequest createGameRequest) {
//        Player player = new Player(createGameRequest.getUsername(), Color.RED, true);
//        playerRepository.save(player);
//
//        Lobby lobby = new Lobby(player);
//        lobbyRepository.save(lobby);
//
//        Game game = new Game(GameType.FFA);
//        gameRepository.save(game);
//
//        player.setLobby(lobby);
//        playerRepository.save(player);
//
//        lobby.setGame(game);
//        lobby.getPlayers().add(player);
//        lobbyRepository.save(lobby);
//
        return ResponseEntity.ok("ruined");
    }

    @Override
    public ResponseEntity<?> joinGame(JoinGameRequest joinGameRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        Player player = new Player();


        Optional<Lobby> optionalLobby = lobbyRepository.findById(joinGameRequest.getLobbyId());
        // check if game exist
        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404", "Lobby with ID: " + joinGameRequest.getLobbyId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();

        //check if game has started yet or not

        // check if game has player with same name:
        if (playerRepository.findPlayerByNameAndLobbyId(lobby.getId(), joinGameRequest.getName()) != null) {
            errorResponse.addError("409", "Name already exists in game.");
        }

        if(!joinGameRequest.getPhone().equals("true")&&!joinGameRequest.getPhone().equals("false")) {
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

        player.setName(joinGameRequest.getName());
        player.setColor(Color.values()[c]);
        player.setPhone(joinGameRequest.getPhone().equals("true"));
        player.setLobby(lobby);
        lobby.getPlayers().add(player);

        playerRepository.save(player);
        lobbyRepository.save(lobby);

        // should return players list ??
        return ResponseEntity.ok(createResponseObject(player));
    }


//    public LobbyResponse createResponseObject(Lobby lobby) {
//        LobbyResponse lobbyResponse = new LobbyResponse(
//                lobby.getId(),
//                lobby.getGame().getId(),
//                lobby.getCanStart(),
//                lobby.getStatus(),
//                lobby.getGame().getGameType().toString(),
//                lobby.getGame().getPoints()
//        );
//
//        return lobbyResponse;
//    }

    public PlayerObjectResponse createResponseObject (Player player) {

        PlayerObjectResponse playerObjectResponse =
                new PlayerObjectResponse(
                        player.getId(),
                        player.getName(),
                        player.getColor().toString(),
                        player.getPhone(),
                        player.getLobby().getId()
                );

        return playerObjectResponse;
    }

}