package com.socialgame.alpha.service;

import com.socialgame.alpha.dto.request.LoginRequest;
import com.socialgame.alpha.dto.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AuthorizationService {

    public ResponseEntity<?> authenticateUser(@Valid LoginRequest loginRequest) {
        return ResponseEntity.ok("registered");
    }

    public ResponseEntity<?> registerUser(@Valid SignupRequest signUpRequest) {
        return ResponseEntity.ok("registered");
    }


}
