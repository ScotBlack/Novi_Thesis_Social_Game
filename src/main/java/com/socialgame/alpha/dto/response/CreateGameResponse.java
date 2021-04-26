package com.socialgame.alpha.dto.response;

public class CreateGameResponse {

    private JwtResponse jwtResponse;
    private LobbyResponse lobbyResponse;

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
