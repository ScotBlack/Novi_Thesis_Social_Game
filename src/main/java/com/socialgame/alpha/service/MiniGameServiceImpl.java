package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.payload.response.ErrorResponse;
import com.socialgame.alpha.payload.response.TeamScoreResponse;
import com.socialgame.alpha.payload.response.minigame.QuestionResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.minigame.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MiniGameServiceImpl implements MiniGameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {this.questionRepository = questionRepository;}

    @Override
    public ResponseEntity<?> nextMiniGame(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

        // selects miniGameType, but since there is only one MiniGameType currently

//        int i = (int) Math.round(Math.random() * (MiniGameType.values().length -1));

//        if (i > 4 || i < 0) {
//            return ResponseEntity.status(200).body("out of bounds");
//        }

        int i = 0;

        switch (MiniGameType.values()[i]) {
            case QUESTION:
                List<Question> questions = questionRepository.findAll();
                int y = (int) Math.round(Math.random() * (questions.size() -1));
                Question question = questions.get(y);
//                return ResponseEntity.status(200).body("got here");
                return ResponseEntity.status(200).body(nextQuestion(question, game));
            case DARE:
                List<Question> questions2 = questionRepository.findAll();
                int z = (int) Math.round(Math.random() * (questions2.size() -1));
                Question question2 = questions2.get(z);
                return ResponseEntity.status(200).body("got to dare " + z);
            case BEST_ANSWER:
                return ResponseEntity.status(200).body("got to best answer");
            case RANKING:
                return ResponseEntity.status(200).body("got to ranking");
            case GUESS_WORD:
                return ResponseEntity.status(200).body("got to guess word");
            default:
                return ResponseEntity.status(200).body("got to default");

        }
    }

    public ResponseEntity<?> nextQuestion(Question miniGame, Game game) {
        game.setCurrentMiniGame(miniGame);

        String[] answers = new String [miniGame.getAllAnswers().size()];
        miniGame.getAllAnswers().toArray(answers);

        List<String> answerList = Arrays.asList(answers);
        Collections.shuffle(answerList);
        answerList.toArray(answers);

        gameRepository.save(game);

        return ResponseEntity.ok(createResponseObject(game, miniGame, answers));
    }

    public QuestionResponse createResponseObject (Game game, Question miniGame, String[] answerList) {


        QuestionResponse questionResponse =
         new QuestionResponse(
             game.getId(),
             miniGame.getMiniGameType(),
             miniGame.getId(),
             game.getCaptains(),
             miniGame.getQuestion(),
             answerList
             );

        return questionResponse;
    }

}
