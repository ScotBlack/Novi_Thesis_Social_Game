package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.PlayerResponse;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService{

    private PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) { this.playerRepository = playerRepository;}


    @Override
    public ResponseEntity<?> findAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return ResponseEntity.ok(players);
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



    private PlayerResponse createResponseObject (Player player) {
        PlayerResponse playerResponse = new PlayerResponse (player.getId(), player.getName(), player.getColor());

        return playerResponse;
    }
}
