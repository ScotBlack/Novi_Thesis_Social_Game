package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.payload.request.CreateGameRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.LobbyResponse;
import com.socialgame.alpha.payload.response.PlayerObjectResponse;
import com.socialgame.alpha.payload.response.TeamResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LobbyServiceImpl implements LobbyService {

    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    @Autowired
    public void setLobbyRepository(LobbyRepository lobbyRepository) {this.lobbyRepository = lobbyRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) {this.teamRepository = teamRepository;}




//    @Override
//    public ResponseEntity<?> createGame(CreateGameRequest createGameRequest) {
//
//
//
//        Player player = new Player(createGameRequest.getName(), Color.RED, true);
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
//
//
//        return ResponseEntity.ok(createResponseObject(lobby));
//    }

//    @Override
//    public ResponseEntity<?> getPlayers(Long id) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);
//
//        if (optionalLobby.isEmpty()) {
//            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        List<Player> players = playerRepository.findPlayersByLobbyId(id);
//
//        return ResponseEntity.ok(createResponseObject(players));
//    }
//
//    @Override
//    public ResponseEntity<?> lobbyStatusUpdate(Long id) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);
//
//        if (optionalLobby.isEmpty()) {
//            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Lobby lobby = optionalLobby.get();
//
//        List<Player> players = playerRepository.findPlayersByLobbyId(lobby.getId());
//
//        Optional<Game> optionalGame = gameRepository.findById(lobby.getGame().getId());
//
//        if (optionalGame.isEmpty()) {
//            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Game game = optionalGame.get();
//
//        // make list of players & phones per color
//        List<Integer> teamsList = new ArrayList<>();
//
//        for (Color color : Color.values()) {
//            int playerNum = 0;
//            int phoneNum = 0;
//
//            for (Player p : players) {
//                if (p.getColor().equals(color)) {
//                    playerNum++;
//                    if (p.getPhone()) {
//                        phoneNum++;
//                    }
//                }
//            }
//            if (playerNum == 1 && phoneNum == 0) { // 1 player / 0 phone = classic
//                teamsList.add(0);
//            } else if (playerNum == 1 && phoneNum == 1) { // 1 player / 1 phone = ffa
//                teamsList.add(1);
//            } else if (playerNum > 1 && phoneNum > 0) { // 2+ players / 1+ phones = team
//                teamsList.add(2);
//            } else if (playerNum > 1 && phoneNum == 0) { // 2+ players / 0 phones = team (without phone)
//                teamsList.add(3);
//            }
//        }
//        boolean canStart = false;
//        String status = null;
//        if (teamsList.size() == 1) {
//            status = "Need at least 2 teams or players";
//        } else if (game.getGameType().equals(GameType.CLASSIC)) {
//            if (teamsList.contains(2) || teamsList.contains(3)) {
//                status = "Every player needs it's own color";
//            } else {
//                canStart = true;
//                status = "Classic game can be started";
//            }
//        } else if (game.getGameType().equals(GameType.FFA)) {
//            if (teamsList.contains(0)) {
//                status = "Every players need it's own phone";
//            } else if (teamsList.contains(2) || teamsList.contains(3)) {
//                status = "Every players need it's own color";
//            } else {
//                canStart = true;
//                status = "FFA game can be started";
//            }
//        } else if (game.getGameType().equals(GameType.TEAMS)) {
//            if (teamsList.contains(0) || teamsList.contains(1)) {
//                status = "Every team needs at least 2 players ";
//            } else if (teamsList.contains(3)) {
//                status = "Every team needs at least 1 player with phone ";
//            } else {
//                canStart = true;
//                status = "Team game can be started";
//            }
//        }
//
//        lobby.setCanStart(canStart);
//        lobby.setStatus(status);
//        lobbyRepository.save(lobby);
//
//        return ResponseEntity.ok(createResponseObject(lobby));
//    }

//    @Override
//    public ResponseEntity<?> setGameType(Long id, GameType gameType) {
//
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        if (!gameType.equals(GameType.CLASSIC) && !gameType.equals(GameType.FFA) && !gameType.equals(GameType.TEAMS)) {
//            errorResponse.addError("404" , "Game type: " + gameType + " is not a valid option.");
//            return ResponseEntity.status(400).body(errorResponse);
//        }
//
//        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);
//
//        if (optionalLobby.isEmpty()) {
//            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Lobby lobby = optionalLobby.get();
//        lobby.getGame().setGameType(gameType);
//        lobbyRepository.save(lobby);
//
//        return ResponseEntity.ok(lobbyStatusUpdate(lobby.getId()));
//    }
//
//    @Override
//    public ResponseEntity<?> setPoints(Long id, int points) {
//
//        ErrorResponse errorResponse = new ErrorResponse();
//        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);
//
//        if (optionalLobby.isEmpty()) {
//            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Lobby lobby = optionalLobby.get();
//        lobby.getGame().setPoints(points);
//        lobbyRepository.save(lobby);
//
//        return ResponseEntity.ok(lobbyStatusUpdate(lobby.getId()));
//    }
//
//    @Override
//    public ResponseEntity<?> startGame(Long id) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);
//
//        if (optionalLobby.isEmpty()) {
//            errorResponse.addError("404", "Lobby with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Lobby lobby = optionalLobby.get();
//
//        Optional<Game> optionalGame = gameRepository.findById(lobby.getGame().getId());
//
//        if (optionalGame.isEmpty()) {
//            errorResponse.addError("404", "Game with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Game game = optionalGame.get();
//
//        // checks if game/lobby meets requirements to start (lobbyStatusUpdate)
//        if (!lobby.getCanStart()) {
//            errorResponse.addError("404", "Game with ID: " + id + " cannot be started right now.");
//            return ResponseEntity.status(403).body(errorResponse);
//        }
//
//        List<Player> players = playerRepository.findPlayersByLobbyId(lobby.getId());
//
//        for (Color color : Color.values()) {
//            Team team = new Team(game, color);
//
//            for (Player player : players) {
//                if (player.getColor().equals(color)) {
//                    team.getPlayers().put(player.getName(),0);
//                }
//            }
//
//            if (!team.getPlayers().isEmpty()) {
//                game.getTeams().add(team);
//                teamRepository.save(team);
//            }
//        }
//
//        game.setStarted(true);
//        gameRepository.save(game);
//
//        //respond team objects
//
//        return ResponseEntity.ok(createResponseObject(game.getTeams()));
//    }
//
//    public Set<TeamResponse> createResponseObject(Set<Team> teams) {
//        Set<TeamResponse> teamsRes = new HashSet<>();
//
//        for (Team team : teams) {
//            TeamResponse response =
//                new TeamResponse(
//                    team.getId(),
//                    team.getGame().getId(),
//                    team.getName().toString(),
//                    team.getPlayers().keySet(),
//                    team.getPoints()
//                );
//            teamsRes.add(response);
//        }
//
//        return teamsRes;
//    }


//    public LobbyResponse createResponseObject(Lobby lobby) {
//        LobbyResponse lobbyResponse = new LobbyResponse (
//            lobby.getId(),
//            lobby.getGame().getId(),
//            lobby.getCanStart(),
//            lobby.getStatus(),
//            lobby.getGame().getGameType().toString(),
//            lobby.getGame().getPoints()
//        );
//
//        return lobbyResponse;
//    }


//    public PlayerObjectResponse createResponseObject (Player player) {
//
//        PlayerObjectResponse playerObjectResponse =
//                new PlayerObjectResponse(
//                        player.getId(),
//                        player.getName(),
//                        player.getColor().toString(),
//                        player.getPhone(),
//                        player.getLobby().getId()
//                );
//
//        return playerObjectResponse;
//    }
//
//    public Set<PlayerObjectResponse> createResponseObject (List<Player> players) {
//        Set<PlayerObjectResponse> playerObjectResponseList = new HashSet<>();
//
//        for (Player player : players) {
//            PlayerObjectResponse playerObjectResponse = createResponseObject(player);
//            playerObjectResponseList.add(playerObjectResponse);
//        }
//
//        return playerObjectResponseList;
//    }


}


