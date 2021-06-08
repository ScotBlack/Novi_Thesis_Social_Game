package com.socialgame.alpha.dto.request;

import javax.validation.constraints.NotBlank;

public class TeamAnswerRequest {

    @NotBlank(message = "Answer is required")
    private String answer;

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}
