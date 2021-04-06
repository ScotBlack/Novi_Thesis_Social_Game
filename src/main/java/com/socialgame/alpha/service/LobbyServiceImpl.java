package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.EColors;
import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.request.CreateGameRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.LobbyResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LobbyServiceImpl implements LobbyService {

    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public void setLobbyRepository(LobbyRepository lobbyRepository) {this.lobbyRepository = lobbyRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}


    @Override
    public ResponseEntity<?> createGame(CreateGameRequest createGameRequest) {

        Game game = new Game("ffa");
        Player player = new Player(createGameRequest.getName(), "RED", true, game);

        gameRepository.save(game);

        Lobby lobby = new Lobby(player, game.getId());

        playerRepository.save(player);
        lobbyRepository.save(lobby);

        return ResponseEntity.ok(createResponseObject(lobby));
    }

    @Override
    public ResponseEntity<?> lobbyStatusUpdate(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();

        List<Player> players = playerRepository.findPlayersByGameId(lobby.getGameId());

        // make list of players & phones per color
        List<Integer> teamsList = new ArrayList<>();
        String[] colors = EColors.colors();

        for (String c : colors) {
            int playerNum = 0;
            int phoneNum = 0;

            for (Player p : players) {
                if (p.getColor().equals(c)) {
                    playerNum++;
                    if (p.getPhone()) {
                        phoneNum++;
                    }
                }
            }
            if (playerNum == 1 && phoneNum == 0) { // 1 player / 0 phone = classic
                teamsList.add(0);
            } else if (playerNum == 1 && phoneNum == 1) { // 1 player / 1 phone = ffa
                teamsList.add(1);
            } else if (playerNum > 1 && phoneNum > 0) { // 2+ players / 1+ phones = team
                teamsList.add(2);
            } else if (playerNum > 1 && phoneNum == 0) { // 2+ players / 0 phones = team (without phone)
                teamsList.add(3);
            }
        }
        boolean canStart = false;
        String status = null;
        if (teamsList.size() == 1) {
            status = "Need at least 2 teams or players";
        } else if (lobby.getGameType().equals("CLASSIC")) {
            if (teamsList.contains(2) || teamsList.contains(3)) {
                status = "Every player needs it's own color";
            } else {
                canStart = true;
                status = "Classic game can be started";
            }
        } else if (lobby.getGameType().equals("FFA")) {
            if (teamsList.contains(0)) {
                status = "Every players need it's own phone";
            } else if (teamsList.contains(2) || teamsList.contains(3)) {
                status = "Every players need it's own color";
            } else {
                canStart = true;
                status = "FFA game can be started";
            }
        } else if (lobby.getGameType().equals("TEAMS")) {
            if (teamsList.contains(0) || teamsList.contains(1)) {
                status = "Every team needs at least 2 players ";
            } else if (teamsList.contains(3)) {
                status = "Every team needs at least 1 player with phone ";
            } else {
                canStart = true;
                status = "Team game can be started";
            }
        }

        lobby.setCanStart(canStart);
        lobby.setStatus(status);
        lobbyRepository.save(lobby);

        return ResponseEntity.ok(createResponseObject(lobby));
    }

    @Override
    public ResponseEntity<?> setGameType(Long id, String gameType) {

        ErrorResponse errorResponse = new ErrorResponse();

        if (!gameType.equals("CLASSIC") && !gameType.equals("FFA") && !gameType.equals("TEAMS")) {
            errorResponse.addError("404" , "Game type: " + gameType + " is not a valid option.");
            return ResponseEntity.status(400).body(errorResponse);
        }

        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();
        lobby.setGameType(gameType);
        lobbyRepository.save(lobby);

        return ResponseEntity.ok(createResponseObject(lobby));
    }

    @Override
    public ResponseEntity<?> setPoints(Long id, int points) {

        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();
        lobby.setPoints(points);
        lobbyRepository.save(lobby);

        return ResponseEntity.ok(createResponseObject(lobby));
    }




    public LobbyResponse createResponseObject(Lobby lobby) {
        LobbyResponse lobbyResponse = new LobbyResponse (
            lobby.getId(),
            lobby.getGameId(),
            lobby.getCanStart(),
            lobby.getStatus(),
            lobby.getGameType(),
            lobby.getPoints()
        );

        return lobbyResponse;
    }
}


