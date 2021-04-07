package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.response.PlayerResponse;
import com.socialgame.alpha.payload.response.LobbyResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {


    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}



//    @Override
//    public ResponseEntity<?> findAllGames() {
//        return ResponseEntity.ok(gameRepository.findAll());
//    }

//    @Override
//    public ResponseEntity<?> findPlayersByGameId(Long id) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        Optional<Game> optionalGame = gameRepository.findById(id);
//
//        if (optionalGame.isEmpty()) {
//            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        List<Player> players = playerRepository.findPlayersByGameId(id);
//
//        return ResponseEntity.ok(createResponseObject(players));
//    }
//
//    @Override
//    public ResponseEntity<?> lobbyHeader(Long id) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        Optional<Game> optionalGame = gameRepository.findById(id);
//
//        if (optionalGame.isEmpty()) {
//            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Game game = optionalGame.get();
//        List<Player> players = playerRepository.findPlayersByGameId(id);
//
//        // make list of players & phones per color
//        List<Integer> teamsList = new ArrayList<>();
//        String[] colors = EColors.colors();
//
//        for (String c : colors) {
//            int playerNum = 0;
//            int phoneNum = 0;
//
//            for (Player p : players) {
//                if (p.getColor().equals(c)) {
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
//            canStart = false;
//            status = "Need at least 2 teams or players";
//        } else if (game.getGameType().equals("classic")) {
//            if (teamsList.contains(2) || teamsList.contains(3)) {
//                canStart = false;
//                status = "Every player needs it's own color";
//            } else {
//                canStart = true;
//                status = "Classic game can be started";
//            }
//        } else if (game.getGameType().equals("ffa")) {
//            if (teamsList.contains(0)) {
//                canStart = false;
//                status = "Every players need it's own phone";
//            } else if (teamsList.contains(2) || teamsList.contains(3)) {
//                canStart = false;
//                status = "Every players need it's own color";
//            } else {
//                canStart = true;
//                status = "FFA game can be started";
//            }
//        } else if (game.getGameType().equals("teams")) {
//            if (teamsList.contains(0) || teamsList.contains(1)) {
//                canStart = false;
//                status = "Every team needs at least 2 players ";
//            } else if (teamsList.contains(3)) {
//                canStart = false;
//                status = "Every team needs at least 1 player with phone ";
//            } else {
//                canStart = true;
//                status = "Team game can be started";
//            }
//        }
//
//        game.setCanStart(canStart);
//        gameRepository.save(game);
//
//        return ResponseEntity.ok(createResponseObject(id, canStart, status, game));
//    }
//
//    @Override
//    public ResponseEntity<?> setGameType(Long id, String gameType) {
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        if (!gameType.equals("classic") && !gameType.equals("ffa") && !gameType.equals("teams")) {
//            errorResponse.addError("404" , "Game type: " + gameType + " is not a valid option.");
//            return ResponseEntity.status(403).body(errorResponse);
//        }
//
//        Optional<Game> optionalGame = gameRepository.findById(id);
//
//        if (optionalGame.isEmpty()) {
//            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Game game = optionalGame.get();
//        game.setGameType(gameType);
//        gameRepository.save(game);
//
//        return lobbyHeader(id);
//    }

//    @Override
//    public ResponseEntity<?> start(Long id) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        Optional<Game> optionalGame = gameRepository.findById(id);
//
//        if (optionalGame.isEmpty()) {
//            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//
//        Game game = optionalGame.get();
//
//        if (!game.getCanStart()) {
//            errorResponse.addError("404" , "Game with ID: " + id + " cannot be started right now.");
//            return ResponseEntity.status(403).body(errorResponse);
//        }
//
//        List<Player> players = playerRepository.findPlayersByGameId(id);
//        String[] colors = EColors.colors();
//        Set<Team> teams = new HashSet<>();
//
//
//        // create Set of Players for every color/team
//        for (String c : colors) {
//            Set<Player> teamPlayers = new HashSet<>();
//
////            loop:
//            for (Player p : players) {
//                if (teamPlayers.isEmpty() && p.getColor().equals(c) && p.getPhone()) {
//                    teamPlayers.add(p);
////                    break;
//                }
//            }
//            for (Player p : players) {
//                if (p.getColor().equals(c) && !teamPlayers.contains(p)) {
//                    teamPlayers.add(p);
//                }
//            }
//
//            if (!teamPlayers.isEmpty()) {
//                teams.add(new Team(c, teamPlayers, game));
//            }
//        }
//
//        for (Team t : teams) {
//            teamRepository.save(t);
//        }
//
//        game.setTeams(teams);
//        gameRepository.save(game);
//
//        return ResponseEntity.ok(createResponseObject(players));
//    }
//

    public PlayerResponse createResponseObject (Player player) {

        PlayerResponse playerResponse =
                new PlayerResponse (
                        player.getId(),
                        player.getName(),
                        player.getColor().toString(),
                        player.getPhone(),
                        player.getGame().getId()
                );

        return playerResponse;
    }

    public LobbyResponse createResponseObject (Long gameId, Boolean canStart, String status, Game game) {
        LobbyResponse lobbyResponse = new LobbyResponse(gameId, gameId, canStart, status, game.getGameType(), game.getPoints()); // bugged due to Lobby Id

        return lobbyResponse;
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
