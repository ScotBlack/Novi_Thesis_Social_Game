package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.dto.request.SettingRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.ResponseBuilder;
import com.socialgame.alpha.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@PreAuthorize("hasRole('GAMEHOST')")
public class HostServiceImpl implements HostService {

    private UserRepository userRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) {this.teamRepository = teamRepository;}

    @Override
    public ResponseEntity<?> setGameSetting(SettingRequest request) {
        String gameIdString = request.getGameIdString();

        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));

        switch (request.getSetting()) {
            case SCORE_TO_WIN:
                game.setScoreToWin(request.getScoreToWin());
                break;
            case GAME_TYPE:
                game.setGameType(request.getGameType());
                break;
            default:
                throw new IllegalArgumentException("Invalid request, please choose a game setting to update.");
        }

        gameRepository.save(game);
        return ResponseEntity.ok(ResponseBuilder.lobbyResponse(game.getLobby()));
    }

    @Override
    public ResponseEntity<?> startGame(HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with: " + username + " does not exist."));

        String gameIdString = user.getGameIdString();
        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));

        if (!game.getLobby().getCanStart()) {
            errorResponse.addError("NOT_READY", "Game with ID: " + user.getGameIdString() + " cannot be started right now.");
            return ResponseEntity.status(403).body(errorResponse);
        }

        Set<Player> players = game.getLobby().getPlayers();

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
            Map<String, Integer> playersMap = team.getPlayers();
            Map.Entry<String, Integer> entry = playersMap.entrySet().iterator().next();

            Optional<Player> optionalPlayer = playerRepository.findByName(entry.getKey());
            if (optionalPlayer.isEmpty()) {
                errorResponse.addError("ENTITY_NOT_FOUND", "Player with name: " + entry.getKey() + " does not exist.");
                return ResponseEntity.status(404).body(errorResponse);
            }

            Player player = optionalPlayer.get();

            User teamUser = player.getUser();
            teamUser.setTeam(team);
            userRepository.save(teamUser);
        }

        game.setStarted(true);
        gameRepository.save(game);

        return ResponseEntity.ok(ResponseBuilder.teamResponseSet(game));
    }
}
