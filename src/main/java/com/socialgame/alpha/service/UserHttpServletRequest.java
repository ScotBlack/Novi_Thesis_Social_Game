package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public final class UserHttpServletRequest {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository;}

    public User retrieveUser(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();

        Optional<User> user = userRepository.findByUsername(username);

        return user.orElseThrow(NoSuchElementException::new);
    }
}
