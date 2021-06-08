package com.socialgame.alpha.dto.response;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.Team;
import com.socialgame.alpha.domain.minigame.MiniGame;
import com.socialgame.alpha.dto.response.minigame.QuestionResponse;
import com.socialgame.alpha.dto.response.minigame.TeamAnswerResponse;

import java.util.HashSet;
import java.util.Set;

public class ResponseBuilder {

    public static LobbyResponse lobbyResponse(Lobby lobby) {
        return new LobbyResponse (
                lobby.getGameIdString(),
                lobby.getCanStart(),
                lobby.getStatus(),
                lobby.getGame().getGameType().name(),
                lobby.getGame().getScoreToWin(),
                playerResponseSet(lobby)
        );
    }

    public static PlayerResponse playerResponse (Player player) {
        return new PlayerResponse(
                player.getUser().getUsername(),
                player.getId(),
                player.getName(),
                player.getColor().toString(),
                player.getPhone()
        );
    }

    public static Set<PlayerResponse> playerResponseSet(Lobby lobby) {
        Set<PlayerResponse> playerResponses = new HashSet<>();

        for (Player player : lobby.getPlayers()) {
            playerResponses.add(playerResponse(player));
        }
        return playerResponses;
    }

    public static TeamResponse teamResponse(Team team) {
        return new TeamResponse(
                        team.getId(),
                        team.getGame().getId(),
                        team.getName().toString(),
                        team.getPlayers().keySet(),
                        team.getPoints()
                );
    }

    public static Set<TeamResponse> teamResponseSet (Game game) {
        Set<TeamResponse> teamsResponses = new HashSet<>();
            for (Team team : game.getTeams()) {
                teamsResponses.add(teamResponse(team));
            }
        return teamsResponses;
    }

    public static QuestionResponse questionResponse(Game game, String[] answerList) {
        Set<Long> teamIds = new HashSet<>();

        for (Team team : game.getCurrentCompetingTeams()) {
            teamIds.add(team.getId());
        }

        return new QuestionResponse(
                        game.getId(),
                        game.getCurrentMiniGame().getMiniGameType(),
                        game.getCurrentMiniGame().getId(),
                        teamIds,
                        game.getCurrentMiniGame().getQuestion(),
                        answerList
                );
    }

    public static TeamAnswerResponse teamAnswerResponse (Team team, MiniGame minigame, String answer, Boolean answerCorrect) {

        return new TeamAnswerResponse (
                team.getId(),
                minigame.getQuestion(),
                answer,
                answerCorrect,
                minigame.getPoints(),
                team.getPoints()
            );
    }
}
