package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.PlayerResponse;
import com.socialgame.alpha.dto.response.TeamResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@PreAuthorize("hasRole('GAMEHOST')")
public class HostServiceImpl implements HostService {

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

    @Override
    public ResponseEntity<?> toggleOtherPlayerColor(Long id) {
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
    public ResponseEntity<?> setGameType(Long id, GameType gameType) {

        ErrorResponse errorResponse = new ErrorResponse();

        if (!gameType.equals(GameType.CLASSIC) && !gameType.equals(GameType.FFA) && !gameType.equals(GameType.TEAMS)) {
            errorResponse.addError("404" , "Game type: " + gameType + " is not a valid option.");
            return ResponseEntity.status(400).body(errorResponse);
        }

        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();
        lobby.getGame().setGameType(gameType);
        lobbyRepository.save(lobby);

        return ResponseEntity.ok("needs fix");
    }

    @Override
    public ResponseEntity<?> setPoints(Long id, int points) {

        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404" , "Lobby with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();
        lobby.getGame().setPoints(points);
        lobbyRepository.save(lobby);

        // needs fix
        return ResponseEntity.ok("needs fix");
    }

    @Override
    public ResponseEntity<?> startGame(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Lobby> optionalLobby = lobbyRepository.findById(id);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404", "Lobby with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();

        Optional<Game> optionalGame = gameRepository.findById(lobby.getGame().getId());

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404", "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

        // checks if game/lobby meets requirements to start (lobbyStatusUpdate)
        if (!lobby.getCanStart()) {
            errorResponse.addError("404", "Game with ID: " + id + " cannot be started right now.");
            return ResponseEntity.status(403).body(errorResponse);
        }

        List<Player> players = playerRepository.findPlayersByLobbyId(lobby.getId());

        for (Color color : Color.values()) {
            Team team = new Team(game, color);

            for (Player player : players) {
                if (player.getColor().equals(color)) {
                    team.getPlayers().put(player.getName(),0);
                }
            }

            if (!team.getPlayers().isEmpty()) {
                game.getTeams().add(team);
                teamRepository.save(team);
            }
        }

        game.setStarted(true);
        gameRepository.save(game);

        //respond team objects
        // User set Team
        // Give users Captain roles/ remove player role?
        // sth to fix
        // delete redundant player objects

        return ResponseEntity.ok(createResponseObject(game.getTeams()));
    }

    public Set<TeamResponse> createResponseObject(Set<Team> teams) {
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


    public PlayerResponse createResponseObject (Player player) {
        return (
                new PlayerResponse(
                        player.getId(),
                        player.getName(),
                        player.getColor().toString(),
                        player.getPhone()
                )
        );
    }
}
