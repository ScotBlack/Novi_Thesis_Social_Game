package com.socialgame.alpha.domain;

public class User {

    private final Long userId;
    private final String userName;


    public User(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
