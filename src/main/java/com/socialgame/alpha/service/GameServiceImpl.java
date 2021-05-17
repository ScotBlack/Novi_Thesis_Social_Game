package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.dto.response.*;
import com.socialgame.alpha.repository.*;
import com.socialgame.alpha.repository.minigame.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public void setLobbyRepository(LobbyRepository lobbyRepository) {this.lobbyRepository = lobbyRepository;}
    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}
    @Autowired
    public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}
    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}
    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) {this.teamRepository = teamRepository;}
    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {this.questionRepository = questionRepository;}

    @Override
    public ResponseEntity<?> findPlayerByID(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player with ID: " + id + " does not exist."));
        return ResponseEntity.ok(ResponseBuilder.playerResponse(player));
    }

    @Override
    public ResponseEntity<?> lobbyStatusUpdate(HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with: " + username + " does not exist."));

        String gameIdString = user.getGameIdString();
        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));

        Set<Player> players = game.getLobby().getPlayers();

        // make list of players & phones per color
        List<Integer> teamsList = new ArrayList<>();

        // see if i can turn this into .stream().map() etc
        for (Color color : Color.values()) {
            int playerNum = 0;
            int phoneNum = 0;

            for (Player p : players) {
                if (p.getColor().equals(color)) {
                    playerNum++;
                    if (p.getPhone()) {
                        phoneNum++;
                    }
                }
            }
            if (playerNum == 1 && phoneNum == 0) { // 1 player / 0 phone = classic
                teamsList.add(0);
            } else if (playerNum == 1 && phoneNum == 1) { // 1 player / 1 phone = ffa
                teamsList.add(1);
            } else if (playerNum > 1 && phoneNum > 0) { // 2+ players / 1+ phones = team
                teamsList.add(2);
            } else if (playerNum > 1 && phoneNum == 0) { // 2+ players / 0 phones = team (without phone)
                teamsList.add(3);
            }
        }
        boolean canStart = false;
        String status = null;
        if (teamsList.size() == 1) {
            status = "Need at least 2 teams or players";
        } else if (game.getGameType().equals(GameType.CLASSIC)) {
            if (teamsList.contains(2) || teamsList.contains(3)) {
                status = "Every player needs it's own color";
            } else {
                canStart = true;
                status = "Classic game can be started";
            }
        } else if (game.getGameType().equals(GameType.FFA)) {
            if (teamsList.contains(0)) {
                status = "Every players need it's own phone";
            } else if (teamsList.contains(2) || teamsList.contains(3)) {
                status = "Every players need it's own color";
            } else {
                canStart = true;
                status = "FFA game can be started";
            }
        } else if (game.getGameType().equals(GameType.TEAMS)) {
            if (teamsList.contains(0) || teamsList.contains(1)) {
                status = "Every team needs at least 2 players";
            } else if (teamsList.contains(3)) {
                status = "Every team needs at least 1 player with phone";
            } else {
                canStart = true;
                status = "Team game can be started";
            }
        }

        game.getLobby().setCanStart(canStart);
        game.getLobby().setStatus(status);
        lobbyRepository.save(game.getLobby());

        return ResponseEntity.ok(ResponseBuilder.lobbyResponse(game.getLobby()));
    }

    @Override
    public ResponseEntity<?> getPlayers(String gameIdString) {
        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));

        return ResponseEntity.ok(ResponseBuilder.playerResponseSet(game.getLobby()));
    }


    @Override
    public ResponseEntity<?> getScore(String gameIdString) {
        ErrorResponse errorResponse = new ErrorResponse();

        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));

        if (!game.getStarted()) {
            errorResponse.addError("NOT_STARTED" , "Game with ID: " + gameIdString + " has not yet started.");
            return ResponseEntity.status(403).body(errorResponse);
        }

        return ResponseEntity.ok(ResponseBuilder.teamResponseSet(game));
    }

    @Override
    public ResponseEntity<?> nextMiniGame(String gameIdString) {
        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));

        for (Team team : game.getTeams()) {
            team.setHasAnswered(false);
            teamRepository.save(team);
        }

        switch (MiniGameType.values()[0]) {
            case QUESTION:
                return nextQuestion(game);
            default:
                return ResponseEntity.status(200).body("Other minigames not yet deployed");
        }
    }

    public ResponseEntity<?> nextQuestion(Game game) {
        List<Question> questions = questionRepository.findAll();

        int i = (int) Math.round(Math.random() * (questions.size() -1));

        if (i > (questions.size() - 1) || i < 0) {
            return ResponseEntity.status(400).body("Question index out of bounds");
        }

        // removes previous competing players
        game.setCurrentCompetingTeams(new HashSet<>());

        for (Team team : game.getTeams()) {
            game.getCurrentCompetingTeams().add(team);
        }

        Question question = questions.get(i);

        String[] answers = new String [question.getAllAnswers().size()];
        question.getAllAnswers().toArray(answers);

        List<String> answerList = Arrays.asList(answers);
        Collections.shuffle(answerList);
        answerList.toArray(answers);

        game.setCurrentMiniGame(question);
        gameRepository.save(game);

        return ResponseEntity.ok(ResponseBuilder.questionResponse(game, answers));
    }
}
