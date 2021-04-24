package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.User;
import com.socialgame.alpha.domain.enums.ERole;
import com.socialgame.alpha.dto.request.LoginRequest;
import com.socialgame.alpha.dto.request.SignupRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.repository.RoleRepository;
import com.socialgame.alpha.repository.UserRepository;
import com.socialgame.alpha.security.UserDetailsImpl;
import com.socialgame.alpha.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;



    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setEncoder(PasswordEncoder passwordEncoder) {
        this.encoder = passwordEncoder;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok("registered");
    }

    public ResponseEntity<?> registerUser(@Valid SignupRequest signUpRequest) {
        ErrorResponse errorResponse = new ErrorResponse();

        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            errorResponse.addError("","Error: Username is already taken!");

            return ResponseEntity.badRequest().body(errorResponse);
        }

        User user = new User(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        //set roles? auto is Player? or Host?

        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName(ERole.HOST).get());

        userRepository.save(user);

        return ResponseEntity.ok("registered: " +signUpRequest.getUsername());
    }


}
