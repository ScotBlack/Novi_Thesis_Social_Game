package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.domain.minigame.Question;
import com.socialgame.alpha.dto.request.TeamAnswerRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.PlayerResponse;
import com.socialgame.alpha.dto.response.minigame.TeamAnswerResponse;
import com.socialgame.alpha.repository.GameRepository;
import com.socialgame.alpha.repository.PlayerRepository;
import com.socialgame.alpha.repository.TeamRepository;
import com.socialgame.alpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;


    @Autowired
    public void setPlayerRepository (PlayerRepository playerRepository) { this.playerRepository = playerRepository;}

    @Autowired
    public void setTeamRepository(TeamRepository teamRepository) { this.teamRepository = teamRepository;}

    @Autowired
    public void setGameRepository(GameRepository gameRepository) { this.gameRepository = gameRepository;}

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository;}


    @Override
    public ResponseEntity<?> togglePlayerColor(Long id, HttpServletRequest request)  {
        ErrorResponse errorResponse = new ErrorResponse();
        // need to check if same player as who clicked button (with token)

//        if (gameHasStarted) {
//
//        }

        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Player jwtPlayer;
        Player player;

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            jwtPlayer = user.getPlayer();
        } else {
            errorResponse.addError("404" , "User with: " + username + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if (optionalPlayer.isPresent()) {
            player = optionalPlayer.get();
            if (!jwtPlayer.equals(player)) {
                errorResponse.addError("400" , "Player can only change it's own color.");
                return ResponseEntity.status(400).body(errorResponse);
            }
        } else {
            errorResponse.addError("404" , "Player with ID: " + id + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }


        Color[] colors = Color.values();

        Color currentColor = player.getColor();
        Color newColor = Color.RED;

        for (int i = 0; i < colors.length; i++) {
            if (currentColor.equals(colors[i]) && i < colors.length - 1) {
                newColor = colors[i + 1];
            }
        }

        player.setColor(newColor);
        playerRepository.save(player);

        return ResponseEntity.ok(createResponseObject(player));
    }

    @Override
    public ResponseEntity<?> teamAnswer(TeamAnswerRequest teamAnswerRequest) {

        // if curentMiniGame is null, then create ErrorResponse

        ErrorResponse errorResponse = new ErrorResponse();

        Optional<Game> optionalGame = gameRepository.findById(teamAnswerRequest.getGameId());

        if (optionalGame.isEmpty()) {
            errorResponse.addError("404", "Game with ID: " + teamAnswerRequest.getGameId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Game game = optionalGame.get();

        Optional<Team> optionalTeam = teamRepository.findById(teamAnswerRequest.getTeamId());

        if (optionalTeam.isEmpty()) {
            errorResponse.addError("404", "Team with ID: " + teamAnswerRequest.getTeamId() + " does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Team team = optionalTeam.get();

        // check if team is in game??

        if (!game.getCurrentCompetingTeams().contains(team)) {
            errorResponse.addError("403", "Team with ID: " + teamAnswerRequest.getTeamId() + " does not compete in this Mini Game.");
        }

        // check if team already answer the question, so they cant get double triple score

        // check if scoreGoal is met

        switch (game.getCurrentMiniGame().getMiniGameType()) {
            case QUESTION:
                Question question = (Question) game.getCurrentMiniGame();
                if (question.getCorrectAnswer().equals(teamAnswerRequest.getAnswer())) {
                    team.setPoints(team.getPoints() + question.getPoints());
                    teamRepository.save(team);
                    return ResponseEntity.ok(createResponseObject(team, question, teamAnswerRequest.getAnswer(),true));
                } else {
                    return ResponseEntity.ok(createResponseObject(team, question,teamAnswerRequest.getAnswer(), false));
                }
            case DARE:
            case BEST_ANSWER:
            case RANKING:
            case GUESS_WORD:
                errorResponse.addError("403", "This Mini Game Type has not yet been deployed.");
                break;
        }

        return ResponseEntity.status(403).body(errorResponse);
    }

    public PlayerResponse createResponseObject (Player player) {
        return (
                new PlayerResponse(
                        player.getId(),
                        player.getName(),
                        player.getColor().toString(),
                        player.getPhone()
                )
        );
    }

    public TeamAnswerResponse createResponseObject (Team team, MiniGame minigame, String answer, Boolean answerCorrect) {

        TeamAnswerResponse response =
                new TeamAnswerResponse (
                        team.getId(),
                        minigame.getQuestion(),
                        answer,
                        answerCorrect,
                        minigame.getPoints(),
                        team.getPoints()
                );

        return response;
    }
}
