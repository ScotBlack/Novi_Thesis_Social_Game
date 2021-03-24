package com.socialgame.alpha.config;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfig {

    @Bean
    CommandLineRunner commandLineRunner(PlayerRepository playerRepository) {
        return args -> {
            Player ian = new Player ("Ian", "RED");
            Player afi = new Player ("Afi", "BLUE");
            Player ben = new Player("Ben", "GREEN");

            playerRepository.save(ian);
            playerRepository.save(afi);
            playerRepository.save(ben);
        };
    }


}