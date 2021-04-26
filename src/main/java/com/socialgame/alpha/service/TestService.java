package com.socialgame.alpha.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String generatePublicContent() {
        return "Public Content.";
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('HOST')")
    public String generateUserContent() {
        return "User Content.";
    }

    @PreAuthorize("hasRole('MODERATOR')")
    public String generateModContent() {
        return "Moderator Board.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String generateAdminContent() {
        return "Admin Board.";
    }

}
