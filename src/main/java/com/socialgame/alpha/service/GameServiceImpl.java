package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.LobbyResponse;
import com.socialgame.alpha.payload.response.PlayerObjectResponse;
import com.socialgame.alpha.payload.response.TeamResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.minigame.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Autowired
    public void setLobbyRepository(LobbyRepository lobbyRepository) {this.lobbyRepository = lobbyRepository;}


    @Override
    public ResponseEntity<?> findAllGames() {
        return ResponseEntity.ok(gameRepository.findAll());
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

        List<Player> players = playerRepository.findPlayersByLobbyId(lobby.getId());

        Optional<Game> optionalGame = gameRepository.findById(lobby.getGame().getId());

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

        // make list of players & phones per color
        List<Integer> teamsList = new ArrayList<>();

        for (Color color : Color.values()) {
            int playerNum = 0;
            int phoneNum = 0;

            for (Player p : players) {
                if (p.getColor().equals(color)) {
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
        } else if (game.getGameType().equals(GameType.CLASSIC)) {
            if (teamsList.contains(2) || teamsList.contains(3)) {
                status = "Every player needs it's own color";
            } else {
                canStart = true;
                status = "Classic game can be started";
            }
        } else if (game.getGameType().equals(GameType.FFA)) {
            if (teamsList.contains(0)) {
                status = "Every players need it's own phone";
            } else if (teamsList.contains(2) || teamsList.contains(3)) {
                status = "Every players need it's own color";
            } else {
                canStart = true;
                status = "FFA game can be started";
            }
        } else if (game.getGameType().equals(GameType.TEAMS)) {
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
    public ResponseEntity<?> getPlayers(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        List<Player> players = playerRepository.findPlayersByLobbyId(id);

        return ResponseEntity.ok(createResponseObject(players));
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


    public LobbyResponse createResponseObject(Lobby lobby) {
        LobbyResponse lobbyResponse = new LobbyResponse (
                lobby.getId(),
                lobby.getGame().getId(),
                lobby.getCanStart(),
                lobby.getStatus(),
                lobby.getGame().getGameType().toString(),
                lobby.getGame().getPoints()
        );

        return lobbyResponse;
    }

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

    public Set<PlayerObjectResponse> createResponseObject (List<Player> players) {
        Set<PlayerObjectResponse> playerObjectResponseList = new HashSet<>();

        for (Player player : players) {
            PlayerObjectResponse playerObjectResponse = createResponseObject(player);
            playerObjectResponseList.add(playerObjectResponse);
        }

        return playerObjectResponseList;
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
