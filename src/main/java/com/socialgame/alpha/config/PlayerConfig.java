package com.socialgame.alpha.config;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfig {

    @Bean
    CommandLineRunner commandLineRunner(PlayerRepository playerRepository, GameRepository gameRepository) {
        return args -> {

            Game testGame = new Game("FFA");
            Game testGame2 = new Game("Teams");

            Player ian = new Player ("Ian", "RED", true, testGame);
            Player afi = new Player ("Afi", "BLUE", true, testGame);
            Player ben = new Player("Ben", "GREEN", true, testGame);

            gameRepository.save(testGame);
            gameRepository.save(testGame2);
            playerRepository.save(ian);
            playerRepository.save(afi);
            playerRepository.save(ben);

        };
    }
}
