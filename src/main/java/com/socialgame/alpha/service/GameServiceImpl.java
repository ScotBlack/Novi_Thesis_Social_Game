package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.domain.enums.MiniGameType;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.LobbyResponse;
import com.socialgame.alpha.dto.response.PlayerResponse;
import com.socialgame.alpha.dto.response.TeamResponse;
import com.socialgame.alpha.dto.response.minigame.QuestionResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.LobbyRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.UserRepository;
import com.socialgame.alpha.repository.minigame.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    private UserHttpServletRequest userRequest;


    @Autowired
    public void setLobbyRepository(LobbyRepository lobbyRepository) {this.lobbyRepository = lobbyRepository;}
    @Autowired
    public void setGameRepository(GameRepository gameRepository) {this.gameRepository = gameRepository;}
    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}
    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository;}
    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {this.questionRepository = questionRepository;}

    @Autowired
    public void setUserRequest(UserHttpServletRequest userRequest) { this.userRequest = userRequest; }

    // whole database
    @Override
    public ResponseEntity<?> findAllGames() {
        return ResponseEntity.ok(gameRepository.findAll());
    }

    @Override
    public ResponseEntity<?> findAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return ResponseEntity.ok(createResponseObject(players));
    }

    // could be used for ingame I guess
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


    // related to particular game
    @Override
    public ResponseEntity<?> lobbyStatusUpdate(HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        User user = userRequest.retrieveUser(request);

        Principal principal = request.getUserPrincipal();
        String username = principal.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);

//        User user;
//
//        if (optionalUser.isPresent()) {
//            user = optionalUser.get();
//        } else {
//            errorResponse.addError("USER_NOT_FOUND" , "User with: " + username + " does not exist.");
//            return ResponseEntity.status(404).body(errorResponse);
//        }

        Optional<Game> optionalGame = gameRepository.findByGameIdString(user.getGameIdString());

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with IdString: " + user.getGameIdString() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();
        Lobby lobby = game.getLobby();

        List<Player> players = playerRepository.findPlayersByGameIdString(lobby.getGameIdString());

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
                status = "Every team needs at least 2 players ";
            } else if (teamsList.contains(3)) {
                status = "Every team needs at least 1 player with phone ";
            } else {
                canStart = true;
                status = "Team game can be started";
            }
        }

        lobby.setCanStart(canStart);
        lobby.setStatus(status);
        lobbyRepository.save(lobby);

        return ResponseEntity.ok(createResponseObject(game));
    }

    @Override
    public ResponseEntity<?> getPlayers(String gameIdString) {
        ErrorResponse errorResponse = new ErrorResponse();

        Optional<Game> optionalGame = gameRepository.findByGameIdString(gameIdString);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("ENTITY_NOT_FOUND"  , "Game with Id String: " + gameIdString + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        List<Player> players = playerRepository.findPlayersByGameIdString(gameIdString);

        return ResponseEntity.ok(createResponseObject(players));
    }

    @Override
    public ResponseEntity<?> getTeams(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();
        Set<Team> teams = game.getTeams();

        return ResponseEntity.ok(createResponseObject(teams)); // needs response object
    }
 
    @Override
    public ResponseEntity<?> getScore(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

        if (!game.getStarted()) {
            errorResponse.addError("403" , "Game with ID: " + id + " has not yet started.");
            return ResponseEntity.status(403).body(errorResponse);
        }

        return ResponseEntity.ok(createResponseObject(game.getTeams()));
    }

    @Override
    public ResponseEntity<?> nextMiniGame(Long id) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404" , "Game with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

        for (Team team : game.getTeams()) {
            team.setHasAnswered(false);
        }

        // selects miniGameType, but since there is only one MiniGameType currently
//        int i = (int) Math.round(Math.random() * (MiniGameType.values().length -1));

//        if (i > 4 || i < 0) {
//            return ResponseEntity.status(200).body("out of bounds");
//        }

        int i = 0;

        switch (MiniGameType.values()[i]) {
            case QUESTION:

                return nextQuestion(game);
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

    public ResponseEntity<?> nextQuestion(Game game) {
        List<Question> questions = questionRepository.findAll();

        int i = (int) Math.round(Math.random() * (questions.size() -1));

        if (i > (questions.size() - 1) || i < 0) {
            return ResponseEntity.status(400).body("Question index out of bounds");
        }

        // remove previous competing players
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

        return ResponseEntity.ok(createResponseObject(game, question, answers));
    }

    public QuestionResponse createResponseObject (Game game, Question miniGame, String[] answerList) {

        Set<Long> teamIds = new HashSet<>();

        for (Team team : game.getCurrentCompetingTeams()) {
            teamIds.add(team.getId());
        }

        QuestionResponse questionResponse =
                new QuestionResponse(
                        game.getId(),
                        miniGame.getMiniGameType(),
                        miniGame.getId(),
                        teamIds,
                        miniGame.getQuestion(),
                        answerList
                );

        return questionResponse;
    }


    public static LobbyResponse createResponseObject(Game game) {
        Set<PlayerResponse> playerResponses = new HashSet<>();

        for (Player player : game.getLobby().getPlayers()) {

            PlayerResponse playerResponse = new PlayerResponse(
                    player.getUser().getUsername(),
                    player.getId(),
                    player.getName(),
                    player.getColor().toString(),
                    player.getPhone()
            );

            playerResponses.add(playerResponse);
        }


        LobbyResponse lobbyResponse = new LobbyResponse (
                game.getGameIdString(),
                game.getLobby().getCanStart(),
                game.getLobby().getStatus(),
                game.getGameType().name(),
                game.getPoints(),
                playerResponses
        );

        return lobbyResponse;
    }

    public PlayerResponse createResponseObject (Player player) {

        PlayerResponse playerResponse =
                new PlayerResponse(
                        player.getUser().getUsername(),
                        player.getId(),
                        player.getName(),
                        player.getColor().toString(),
                        player.getPhone()
                );

        return playerResponse;
    }

    public Set<PlayerResponse> createResponseObject (List<Player> players) {
        Set<PlayerResponse> playerObjectResponseList = new HashSet<>();

        for (Player player : players) {
            PlayerResponse playerObjectResponse = createResponseObject(player);
            playerObjectResponseList.add(playerObjectResponse);
        }

        return playerObjectResponseList;
    }

    public static Set<TeamResponse> createResponseObject (Set<Team> teams) {
        Set<TeamResponse> teamsRes = new HashSet<>();

        for (Team team : teams) {
            TeamResponse response =
                    new TeamResponse(
                            team.getId(),
                            team.getGame().getId(),
                            team.getName().toString(),
                            team.getPlayers().keySet(),
                            team.getPoints()
                    );
            teamsRes.add(response);
        }

        return teamsRes;
    }
}
