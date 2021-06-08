package com.socialgame.alpha.configuration;

import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.repository.minigame.MiniGameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiniGameConfig {

    @Bean
    CommandLineRunner commandLineRunner(MiniGameRepository miniGameRepository) {
        return args -> {
           String[] answers = new String[3];
            answers[0] ="wrong A";
            answers[1] = "wrong B";
            answers[2] = "wrong C";

            Question one = new Question(MiniGameType.QUESTION, "What color is a Banana", "Yellow", answers);
            Question two = new Question(MiniGameType.QUESTION,"Capital of Peru", "Lima", answers);
            Question three = new Question(MiniGameType.QUESTION,"2 + 2", "4", answers);
            Question four = new Question(MiniGameType.QUESTION,"Lead singer Queen?", "Freddie Mercury", answers);

            miniGameRepository.save(one);
            miniGameRepository.save(two);
            miniGameRepository.save(three);
            miniGameRepository.save(four);
        };
    }
}