package com.socialgame.alpha.repository;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.prototype.GamePrototype;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.setGameIdString("abc");
    }


    @Test
    void existsByGameIdString() {
        gameRepository.save(GamePrototype.aGame());


    }
    @AutoConfigureTestDatabase

    @Test
    void findByGameIdString() {
        gameRepository.save(GamePrototype.aGame());

        Game foundGame = gameRepository.findByGameIdString("abc").get();

        Assertions.assertTrue(foundGame instanceof Game);
        assertThat(foundGame.getGameIdString()).isEqualTo("abc");
    }
}