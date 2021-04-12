package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.request.NewPlayerRequest;
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
public class PlayerServiceImpl implements PlayerService{

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
    public ResponseEntity<?> joinLobby(NewPlayerRequest newPlayerRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        Player player = new Player();


        Optional<Lobby> optionalLobby = lobbyRepository.findById(newPlayerRequest.getLobbyId());
        // check if game exist
        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404", "Lobby with ID: " + newPlayerRequest.getLobbyId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();


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

//    public ResponseEntity<?> playerAnswer(PlayerAnswerRequest playerAnswerRequest) {
//        ErrorResponse errorResponse = new ErrorResponse();
//
//       Optional<Game> optionalGame = gameRepository.findById(playerAnswerRequest.getGameId());
//
//       if (optionalGame.isEmpty()) {
//           errorResponse.addError("404", "Game with ID: " + playerAnswerRequest.getGameId() + " does not exist.");
//           return ResponseEntity.status(404).body(errorResponse);
//       }
//
//       Game game = optionalGame.get();
//
//
//       Optional<Player> optionalPlayer = playerRepository.findById(playerAnswerRequest.getPlayerId());
//
//       if (optionalPlayer.isEmpty()) {
//           errorResponse.addError("404", "Player with ID: " + playerAnswerRequest.getPlayerId() + " does not exist.");
//           return ResponseEntity.status(404).body(errorResponse);
//       }
//
//       Player player = optionalPlayer.get();
//
//       if (!game.getCaptains().contains(player.getId())) {
//           errorResponse.addError("403", "Player with ID: " + playerAnswerRequest.getPlayerId() + " is not competing in this Mini Game.");
//           return ResponseEntity.status(403).body(errorResponse);
//       }
//
//       Boolean answer;
//
//       switch (game.getCurrentMiniGame().getMiniGameType()) {
//           case QUESTION:
//               Question question = (Question) game.getCurrentMiniGame();
//               if (question.getCorrectAnswer().equals(playerAnswerRequest.getAnswer())) {
//                   player.setPoints(player.getPoints() + question.getPoints());
//                   answer = true;
//               } else {
//                   answer = false;
//               }
//               break;
//           case DARE:
//           case BEST_ANSWER:
//           case RANKING:
//           case GUESS_WORD:
//               errorResponse.addError("403", "This Mini Game Type has not yet been deployed.");
//               break;
//       }
//
//
//
//
//       return ResponseEntity.status(403).body(errorResponse);
//    }
//
//    public PlayerAnswerResponse createResponseEntity (Long gameId, Long playerId, ) {
//
//    }




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
