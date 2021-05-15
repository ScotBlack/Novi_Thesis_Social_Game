package com.socialgame.alpha.dto.request;

import com.socialgame.alpha.domain.enums.MiniGameType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TeamAnswerRequest {

//    @NotNull
//    private String gameIdString;

    @NotBlank(message = "Answer is required")
    private String answer;

//    public String getGameIdString() {
//        return gameIdString;
//    }
//    public void setGameIdString(String gameIdString) {
//        this.gameIdString = gameIdString;
//    }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

}
