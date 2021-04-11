package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.payload.request.NewPlayerRequest;
import com.socialgame.alpha.payload.request.PlayerAnswerRequest;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.PlayerAnswerResponse;
import com.socialgame.alpha.payload.response.PlayerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.minigame.MiniGameRepository;
import com.socialgame.alpha.repository.minigame.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService{

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private MiniGameRepository miniGameRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) { this.playerRepository = playerRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) { this.gameRepository = gameRepository;}

    @Autowired
    public void setMiniGameRepository(MiniGameRepository miniGameRepository) { this.miniGameRepository = miniGameRepository;}

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) { this.questionRepository = questionRepository;}

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
    public ResponseEntity<?> joinGame(NewPlayerRequest newPlayerRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        Player player = new Player();

        // check if game exist
        if (gameRepository.findById(newPlayerRequest.getGameId()).isEmpty()) {
            errorResponse.addError("404", "Game with ID: " + newPlayerRequest.getGameId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = gameRepository.findById(newPlayerRequest.getGameId()).get();

        // check if game has player with same name:
        if (playerRepository.findPlayerByNameAndGameId(game.getId(), newPlayerRequest.getName()) != null) {
            errorResponse.addError("409", "Name already exists in game.");
        }

        if(!newPlayerRequest.getPhone().equals("true")&&!newPlayerRequest.getPhone().equals("false")) {
            errorResponse.addError("406", "Phone must be either true or false");
        }

        if (errorResponse.getErrors().size() > 0) {
            return ResponseEntity.status(400).body(errorResponse);
        }

        int c = 0;
        for (int i = 0; i < game.getPlayers().size(); i++) {
            c++;
            if (c == 8) {
                c = 0;
            }
        }

        player.setName(newPlayerRequest.getName());
        player.setColor(Color.values()[c]);
        player.setPhone(newPlayerRequest.getPhone().equals("true"));
        player.setGame(game);
        game.addPlayer(player);

        playerRepository.save(player);
        gameRepository.save(game);

        return ResponseEntity.ok(createResponseObject(player));
    }

    public ResponseEntity<?> playerAnswer(PlayerAnswerRequest playerAnswerRequest) {
        ErrorResponse errorResponse = new ErrorResponse();

       Optional<Game> optionalGame = gameRepository.findById(playerAnswerRequest.getGameId());

       if (optionalGame.isEmpty()) {
           errorResponse.addError("404", "Game with ID: " + playerAnswerRequest.getGameId() + " does not exist.");
           return ResponseEntity.status(404).body(errorResponse);
       }

       Game game = optionalGame.get();


       Optional<Player> optionalPlayer = playerRepository.findById(playerAnswerRequest.getPlayerId());

       if (optionalPlayer.isEmpty()) {
           errorResponse.addError("404", "Player with ID: " + playerAnswerRequest.getPlayerId() + " does not exist.");
           return ResponseEntity.status(404).body(errorResponse);
       }

       Player player = optionalPlayer.get();

       if (!game.getCaptains().contains(player.getId())) {
           errorResponse.addError("403", "Player with ID: " + playerAnswerRequest.getPlayerId() + " is not competing in this Mini Game.");
           return ResponseEntity.status(403).body(errorResponse);
       }

       Boolean answer;

       switch (game.getCurrentMiniGame().getMiniGameType()) {
           case QUESTION:
               Question question = (Question) game.getCurrentMiniGame();
               if (question.getCorrectAnswer().equals(playerAnswerRequest.getAnswer())) {
                   player.setPoints(player.getPoints() + question.getPoints());
                   answer = true;
               } else {
                   answer = false;
               }
               break;
           case DARE:
           case BEST_ANSWER:
           case RANKING:
           case GUESS_WORD:
               errorResponse.addError("403", "This Mini Game Type has not yet been deployed.");
               break;
       }




       return ResponseEntity.status(403).body(errorResponse);
    }

    public PlayerAnswerResponse createResponseEntity (Long gameId, Long playerId, ) {

    }




    public PlayerResponse createResponseObject (Player player) {
        return (
            new PlayerResponse (
                player.getId(),
                player.getName(),
                player.getColor().toString(),
                player.getPhone(),
                player.getGame().getId()
            )
        );
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
