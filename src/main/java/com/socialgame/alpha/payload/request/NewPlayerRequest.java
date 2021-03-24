package com.socialgame.alpha.payload.request;


import javax.validation.constraints.NotBlank;

public class NewPlayerRequest {

    @NotBlank(message = "Name is required")
    private String name;

//    @NotBlank(message = "PhonePresent is required ")
    private Boolean phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getPhone() { return phone; }
    public void setPhone(Boolean phonePresent) { this.phone = phonePresent; }
}
