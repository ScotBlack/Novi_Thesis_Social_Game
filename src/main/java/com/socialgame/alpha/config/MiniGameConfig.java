package com.socialgame.alpha.config;

import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.repository.minigame.MiniGameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;


@Configuration
public class MiniGameConfig {




    @Bean
    CommandLineRunner commandLineRunner(MiniGameRepository miniGameRepository) {
        return args -> {
            HashSet<String> answers = new HashSet<>();
            answers.add("wrong A");
            answers.add("wrong B");
            answers.add("wrong C");

            Question one = new Question("What color is a Banana", "Yellow", answers, "Biology" );
            Question two = new Question("Capital of Peru", "Lima", answers, "Geography" );
            Question three = new Question("2 + 2", "4", answers, "Math" );
            Question four = new Question("Lead singer Queen?", "Freddie Mercury", answers, "Pop" );

            miniGameRepository.save(one);
            miniGameRepository.save(two);
            miniGameRepository.save(three);
            miniGameRepository.save(four);
        };

    }
}