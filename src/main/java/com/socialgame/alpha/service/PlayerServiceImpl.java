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

import javax.persistence.EntityNotFoundException;
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
        Color newColor = null;
        String username = request.getUserPrincipal().getName();
        Player player1 = userRepository.findByUsername(username)
                .orElseThrow(() -> new  EntityNotFoundException("User with: " + username + " does not exist."))
                .getPlayer();

        Player player2 = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player with ID: " + id + " does not exist."));

        if (!player1.equals(player2)) {
            throw new IllegalArgumentException("You may only change your own color");
        }

        for (int i = 0; i < Color.values().length - 1; i++) {
            if (player2.getColor().equals(Color.values()[i])) {
                newColor = Color.values()[i + 1];
            }
        }

        player2.setColor(newColor == null ? Color.RED : newColor);
        playerRepository.save(player2);

        return ResponseEntity.ok(createResponseObject(player2));
    }

    @Override
    public ResponseEntity<?> teamAnswer(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with: " + username + " does not exist."));

        String gameIdString = user.getGameIdString();
        Game game = gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."));


        if (!game.getStarted())  throw new IllegalArgumentException("Game has not yet started.");

        if (user.getTeam()== null) throw new IllegalArgumentException("User doesn't have any team assigned, fatal error for game.");
        Team team = user.getTeam();

        if (!game.getCurrentCompetingTeams().contains(team)) throw new IllegalArgumentException("Team: " + team.getName() + " does not compete in this MiniGame.");

        if (team.getHasAnswered()) throw new IllegalArgumentException("Team: " + team.getName() + " has already answered.");

        switch (game.getCurrentMiniGame().getMiniGameType()) {
            case QUESTION:
                return ResponseEntity.ok(answerQuestion(game,team));
            case DARE:
            case BEST_ANSWER:
            case RANKING:
            case GUESS_WORD:
                break;
        }

        throw new IllegalArgumentException("This should not happen.");
    }

    public ResponseEntity<?> answerQuestion(Game game, Team team) {
        Question question = (Question) game.getCurrentMiniGame();
        team.setHasAnswered(true);

        if (!question.getCorrectAnswer().equals("Lima")) {
            return ResponseEntity.ok(createResponseObject(team, question, "Lima", false));
        }

        team.setPoints(team.getPoints() + question.getPoints());
        teamRepository.save(team);

        if (team.getPoints() >= game.getScoreToWin()) {
            return ResponseEntity.ok("You have won the game!");
        }

        return ResponseEntity.ok(createResponseObject(team, question, "Lima",true));
    }


    public PlayerResponse createResponseObject (Player player) {
        return (
                new PlayerResponse(
                        player.getUser().getUsername(),
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
