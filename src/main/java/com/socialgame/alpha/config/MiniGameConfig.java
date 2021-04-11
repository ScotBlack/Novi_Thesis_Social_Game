package com.socialgame.alpha.config;

import com.socialgame.alpha.domain.enums.AgeSetting;
import com.socialgame.alpha.domain.enums.MiniGameType;
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
           String[] answers = new String[3];
            answers[0] ="wrong A";
            answers[1] = "wrong B";
            answers[2] = "wrong C";

            Question one = new Question(MiniGameType.QUESTION, "What color is a Banana", AgeSetting.FAMILY, "Yellow", answers, "Biology" );
            Question two = new Question(MiniGameType.QUESTION,"Capital of Peru", AgeSetting.FAMILY, "Lima", answers, "Geography" );
            Question three = new Question(MiniGameType.QUESTION,"2 + 2", AgeSetting.FAMILY, "4", answers, "Math" );
            Question four = new Question(MiniGameType.QUESTION,"Lead singer Queen?", AgeSetting.FAMILY, "Freddie Mercury", answers, "Pop" );

            miniGameRepository.save(one);
            miniGameRepository.save(two);
            miniGameRepository.save(three);
            miniGameRepository.save(four);
        };

    }
}