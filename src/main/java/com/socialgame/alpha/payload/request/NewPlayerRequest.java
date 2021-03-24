package com.socialgame.alpha.payload.request;


import javax.validation.constraints.*;

public class NewPlayerRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phonePresent) { this.phone = phonePresent; }
}
