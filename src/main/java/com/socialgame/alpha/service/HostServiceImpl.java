package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.SetGamePointsRequest;
import com.socialgame.alpha.dto.request.SetGameTypeRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.PlayerResponse;
import com.socialgame.alpha.dto.response.TeamResponse;
import com.socialgame.alpha.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Service
@PreAuthorize("hasRole('GAMEHOST')")
public class HostServiceImpl implements HostService {

    private LobbyRepository lobbyRepository;
    private UserRepository userRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository;}

    @Autowired
    public void setLobbyRepository(LobbyRepository lobbyRepository) {this.lobbyRepository = lobbyRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) {this.teamRepository = teamRepository;}

    @Override
    public ResponseEntity<?> setGameType(SetGameTypeRequest setGameTypeRequest) {
        String gameIdString = setGameTypeRequest.getGameIdString();
        GameType gameType = setGameTypeRequest.getGameType();

        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findByGameIdString(gameIdString);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("ENTITY_NOT_FOUND"  , "Game with Id String: " + gameIdString + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = gameRepository.findByGameIdString(gameIdString).get();
        game.setGameType(gameType);
        gameRepository.save(game);

        return ResponseEntity.ok(GameServiceImpl.createResponseObject(game));
    }

    @Override
    public ResponseEntity<?> setPoints(SetGamePointsRequest setGamePointsRequest) {
        String gameIdString = setGamePointsRequest.getGameIdString();
        int gamePoints = setGamePointsRequest.getGamePoints();

        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findByGameIdString(gameIdString);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("ENTITY_NOT_FOUND"  , "Game with Id String: " + gameIdString + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();
        game.setPoints(gamePoints);
        gameRepository.save(game);

        return ResponseEntity.ok(GameServiceImpl.createResponseObject(game));
    }

    @Override
    public ResponseEntity<?> startGame(HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        Principal principal = request.getUserPrincipal();
        String username = principal.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            errorResponse.addError("USER_NOT_FOUND" , "User with: " + username + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Optional<Game> optionalGame = gameRepository.findByGameIdString(user.getGameIdString());

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with IdString: " + user.getGameIdString() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();
        Lobby lobby = game.getLobby();

        if (!lobby.getCanStart()) {
            errorResponse.addError("NOT_READY", "Game with ID: " + user.getGameIdString() + " cannot be started right now.");
            return ResponseEntity.status(403).body(errorResponse);
        }

        Set<Player> players = lobby.getPlayers();

        for (Color color : Color.values()) {
            Team team = new Team(game, color);

            for (Player player : players) {
               if (player.getColor().equals(color)) {
                    team.getPlayers().put(player.getName(), 0);
                }
            }

            if (!team.getPlayers().isEmpty()) {
                game.getTeams().add(team);
                teamRepository.save(team);
            }
        }

        for (Team team : game.getTeams()) {
            Map<String,Integer> playersMap = team.getPlayers();
            Map.Entry<String,Integer> entry = playersMap.entrySet().iterator().next();

            Optional<Player> optionalPlayer = playerRepository.findByName(entry.getKey());
            if (optionalPlayer.isEmpty()) {
                errorResponse.addError("ENTITY_NOT_FOUND", "Player with name: " + entry.getKey() + " does not exist.");
                return ResponseEntity.status(404).body(errorResponse);
            }

            Player player = optionalPlayer.get();

            User teamUser = player.getUser();
            user.setTeam(team);
            userRepository.save(teamUser);
        }



        game.setStarted(true);
        gameRepository.save(game);

        return ResponseEntity.ok(GameServiceImpl.createResponseObject(game.getTeams()));
    }
        // respond team objects
        // User set Team
        // Give users Captain roles/ remove player role?
        // sth to fix
        // delete redundant player objects
}
