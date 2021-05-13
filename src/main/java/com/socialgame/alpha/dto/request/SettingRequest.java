package com.socialgame.alpha.dto.request;

import com.socialgame.alpha.domain.enums.GameSetting;
import com.socialgame.alpha.domain.enums.GameType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SettingRequest {

    @NotBlank
    private String gameIdString;

    @NotNull
    private GameSetting setting;

    private int scoreToWin;

    private GameType gameType;

    public String getGameIdString() {
        return gameIdString;
    }
    public void setGameIdString(String gameIdString) {
        this.gameIdString = gameIdString;
    }

    public GameSetting getSetting() {
        return setting;
    }
    public void setSetting(GameSetting setting) {
        this.setting = setting;
    }

    public int getScoreToWin() {
        return scoreToWin;
    }
    public void setScoreToWin(int scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    public GameType getGameType() {
        return gameType;
    }
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
