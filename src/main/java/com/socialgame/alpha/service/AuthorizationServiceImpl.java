package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.ERole;
import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.dto.request.LoginRequest;
import com.socialgame.alpha.dto.response.*;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
public class AuthorizationServiceImpl implements AuthorizationService {

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

    @Override
    public ResponseEntity<?> createGame (CreateGameRequest createGameRequest) {
        ErrorResponse errorResponse = new ErrorResponse();

        Boolean uniqueGameIdString = false;
        String gameIdString ="placeholder";
        int leftLimit = 66; // letter 'A'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
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
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_GAMEHOST).get());
        userRepository.save(user);

        Player player = new Player(createGameRequest.getUsername(), Color.RED, true);
        playerRepository.save(player);

        Lobby lobby = new Lobby(player, gameIdString);
        lobbyRepository.save(lobby);

        player.setLobby(lobby);
        playerRepository.save(player);

        Game game = new Game(gameIdString);
        gameRepository.save(game);


        playerRepository.save(player);

        lobby.setGame(game);
        lobbyRepository.save(lobby);

        JwtResponse jwtResponse =
                authenticateUser(
                        username,
                        gameIdString
                );

        return ResponseEntity.ok(createResponseObject(jwtResponse, lobby));
    }

    @Override
    public ResponseEntity<?> joinGame (JoinGameRequest joinGameRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        String username = joinGameRequest.getUsername();
        String gameIdString = joinGameRequest.getGameIdString();

        // find Lobby

        Optional<Lobby> optionalLobby = lobbyRepository.findByGameIdString(gameIdString);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404", "Entity not found: " + gameIdString + " Lobby does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();

        //TODO - check if game has started yet or not

        //create User

        String requestedUsername = gameIdString + "_" + username;

        if (Boolean.TRUE.equals(userRepository.existsByUsername(requestedUsername))) {
            errorResponse.addError("422", "Unprocessable Entity: Username already exists in this game.");
            return ResponseEntity.status(422).body(errorResponse);
        }

        User user = new User(requestedUsername, encoder.encode(gameIdString));
        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_PLAYER).get());
        userRepository.save(user);

        // create Player

        // determines what starting color Player should have
        int c = 0;
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            c++;
            if (c == 8) {
                c = 0;
            }
        }

        Player player = new Player(username, Color.values()[c], true);
        player.setLobby(lobby);
        playerRepository.save(player);

        lobby.getPlayers().add(player);
        lobbyRepository.save(lobby);

        JwtResponse jwtResponse =
                authenticateUser(
                        requestedUsername,
                        gameIdString
                );

        return ResponseEntity.ok(createResponseObject(jwtResponse, lobby));
    }

    // merge with Joingame
    @Override
    public ResponseEntity<?> rejoin (JoinGameRequest joinGameRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        String username = joinGameRequest.getUsername();
        String gameIdString = joinGameRequest.getGameIdString();

        // find Lobby
        Optional<Lobby> optionalLobby = lobbyRepository.findByGameIdString(gameIdString);

        if (optionalLobby.isEmpty()) {
            errorResponse.addError("404", "Entity not found: " + gameIdString + " Lobby does not exist.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Lobby lobby = optionalLobby.get();

        JwtResponse jwtResponse =
                authenticateUser(
                        joinGameRequest.getUsername(),
                        joinGameRequest.getGameIdString()
                );

        return ResponseEntity.ok(createResponseObject(jwtResponse, lobby));
    }


    public JwtResponse authenticateUser (String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse (jwt, userDetails.getId(),userDetails.getUsername(), roles);
    }


    public AuthGameResponse createResponseObject (JwtResponse jwtResponse, Lobby lobby) {

        Set<PlayerResponse> playerResponses = new HashSet<>();

        for (Player player : lobby.getPlayers()) {

            PlayerResponse playerResponse = new PlayerResponse(
                    player.getId(),
                    player.getName(),
                    player.getColor().toString(),
                    player.getPhone()
            );

            playerResponses.add(playerResponse);
        }

        LobbyResponse lobbyResponse = new LobbyResponse (
                lobby.getGameIdString(),
                lobby.getCanStart(),
                lobby.getStatus(),
                lobby.getGame().getGameType().toString(),
                lobby.getGame().getPoints(),
                playerResponses
        );

        return new AuthGameResponse(jwtResponse, lobbyResponse);
    }



}