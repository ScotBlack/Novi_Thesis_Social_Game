package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.ERole;
import com.socialgame.alpha.domain.enums.GameType;
import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.LoginRequest;
import com.socialgame.alpha.dto.request.SignupRequest;
import com.socialgame.alpha.dto.response.ErrorResponse;
import com.socialgame.alpha.dto.response.JwtResponse;
import com.socialgame.alpha.dto.response.MessageResponse;
import com.socialgame.alpha.repository.*;
import com.socialgame.alpha.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class AuthorizationService {

    private static final String ROLE_NOT_FOUND_ERROR = "Error: Role is not found.";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private LobbyRepository lobbyRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
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
    public void setLobbyRepository(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder passwordEncoder) {
        this.encoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     *
     * Deze methode verwerkt de gebruiker die wil registreren. De username en e-mail worden gecheckt. Eventuele rollen
     * worden toegevoegd en de gebruiker wordt opgeslagen in de database.
     *
     * @param signUpRequest de payload signup-request met gebruikersnaam en wachtwoord.
     * @return een HTTP response met daarin een succesbericht.
     */
//    public ResponseEntity<?> registerUser(@Valid SignupRequest signUpRequest) {
//        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(authenticateUser(signUpRequest.getUsername(), signUpRequest.getPassword()));
//    }

    /**
     * Deze methode controleert de ontvangen username en wachtwoord. Het gebruikt hiervoor de
     * AuthenticationManager. I.a.w. Spring security doet die allemaal voor ons.
     *
     * Wanneer de gebruikersnaam/wachtwoord combinatie niet klopt, wordt er een Runtime exception gegooid:
     * 401 Unauthorized. Deze wordt gegooid door
     * {@link nl.novi.stuivenberg.springboot.example.security.service.security.jwt.AuthEntryPointJwt}
     *
     *
     * @param loginRequest De payload met username en password.
     * @return een HTTP-response met daarin de JWT-token.
     */
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    public ResponseEntity<?> createGame (CreateGameRequest createGameRequest) {
        ErrorResponse errorResponse = new ErrorResponse();

        Boolean uniqueGameIdString = false;
        String gameIdString ="placeholder";
        int leftLimit = 66; // letter 'A'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;
        Random random = new Random();

        // loops until unique String is generated
        while (!uniqueGameIdString) {
            gameIdString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            if (Boolean.FALSE.equals(gameRepository.existsByGameIdString(gameIdString))) {
                uniqueGameIdString = true;
            }
        }

        if (gameIdString.equals("placeholder")) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Something went wrong generating gameIdString"));
        }

        String  username = gameIdString + "_" + createGameRequest.getUsername();

        // unnecessary
        if (Boolean.TRUE.equals(userRepository.existsByUsername(username))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + createGameRequest.getUsername() + " is already taken!"));
        }

        User user = new User(username, encoder.encode(gameIdString));
        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName(ERole.HOST).get());
        userRepository.save(user);

        Player player = new Player(createGameRequest.getUsername(), Color.RED, true);
        playerRepository.save(player);

        Lobby lobby = new Lobby(player);
        lobbyRepository.save(lobby);

        Game game = new Game(gameIdString);
        gameRepository.save(game);

        player.setLobby(lobby);
        playerRepository.save(player);

        lobby.setGame(game);
        lobby.getPlayers().add(player);
        lobbyRepository.save(lobby);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,
                        gameIdString));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        // create user + add random String for uniqueness in whole database with role HOST
            // set Game id as password?
        // authenticate user

        // create player
        // create lobby(player)
        // create game

        // return JwtResponse + GameDetails
//        return ResponseEntity.ok(user);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    public ResponseEntity<?> joinGame () {

        // check if Lobby has Player with username

        // create user + add random String for uniqueness in whole database
        // authenticate user

        // create player with role PLAYER
        // player Join Lobby



        // return JwtResponse + GameDetails

    return ResponseEntity.ok("lol");
    }




}
