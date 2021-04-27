package com.socialgame.alpha.dto.response;

public class AuthGameResponse {

    private JwtResponse jwtResponse;
    private LobbyResponse lobbyResponse;

    public AuthGameResponse(JwtResponse jwtResponse, LobbyResponse lobbyResponse) {
        this.jwtResponse = jwtResponse;
        this.lobbyResponse = lobbyResponse;
    }

    public JwtResponse getJwtResponse() {
        return jwtResponse;
    }
    public void setJwtResponse(JwtResponse jwtResponse) {
        this.jwtResponse = jwtResponse;
    }

    public LobbyResponse getLobbyResponse() {
        return lobbyResponse;
    }
    public void setLobbyResponse(LobbyResponse lobbyResponse) {
        this.lobbyResponse = lobbyResponse;
    }
}
