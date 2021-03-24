package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Override
    public ResponseEntity<?> findAllGames() {
        return ResponseEntity.ok(gameRepository.findAll());
    }

    @Override
    public ResponseEntity<?> findAllPlayersInGame(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

//        game.getPlayers();

        return ResponseEntity.ok(game.getPlayers());
    }

}
