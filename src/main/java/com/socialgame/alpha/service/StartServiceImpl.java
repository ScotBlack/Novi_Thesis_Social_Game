package com.socialgame.alpha.service;

import com.socialgame.alpha.domain.*;
import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.ERole;
import com.socialgame.alpha.dto.request.CreateGameRequest;
import com.socialgame.alpha.dto.request.JoinGameRequest;
import com.socialgame.alpha.dto.response.*;
import com.socialgame.alpha.repository.*;
import com.socialgame.alpha.configuration.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StartServiceImpl implements StartService {

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

    @Override
    public ResponseEntity<?> createGame (CreateGameRequest createGameRequest) {
        String gameIdString = initializeGame(createGameRequest);
        String username = gameIdString + "_" + createGameRequest.getUsername();

        JwtResponse jwtResponse = authenticateUser(username, gameIdString);

        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<?> joinGame (JoinGameRequest joinGameRequest) {
        String username = initializePlayer(joinGameRequest);

        JwtResponse jwtResponse = authenticateUser(username, joinGameRequest.getGameIdString());

        return ResponseEntity.ok(jwtResponse);
    }

    public String initializeGame (CreateGameRequest createGameRequest) {
        ErrorResponse errorResponse = new ErrorResponse();

        String gameIdString = generateGameIdString();
        String  username = gameIdString + "_" + createGameRequest.getUsername();

        if (gameIdString.equals("placeholder")) throw new IllegalArgumentException("Error: Something went wrong generating gameIdString");

        if (Boolean.TRUE.equals(userRepository.existsByUsername(username))) {
            throw new IllegalArgumentException( "Error: " + createGameRequest.getUsername() + " is already taken!");
        }

        User user = new User(username, encoder.encode(gameIdString), gameIdString);
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_GAMEHOST).get());
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_PLAYER).get());
        userRepository.save(user);

        Player player = new Player(gameIdString, user, createGameRequest.getUsername(), Color.RED, true);
        playerRepository.save(player);

        user.setPlayer(player);
        userRepository.save(user);

        Lobby lobby = new Lobby(player, gameIdString);
        lobbyRepository.save(lobby);

        player.setLobby(lobby);
        playerRepository.save(player);

        Game game = new Game(gameIdString);
        game.setLobby(lobby);
        gameRepository.save(game);

        lobby.setGame(game);
        lobbyRepository.save(lobby);

        return gameIdString;
    }

    public String initializePlayer(JoinGameRequest joinGameRequest) {
        String username = joinGameRequest.getUsername() ;
        String gameIdString = joinGameRequest.getGameIdString();
        String requestedUsername = gameIdString + "_" + username;

        // find Lobby
        Lobby lobby = lobbyRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Lobby with: " + gameIdString + " does not exist."));

        if (gameRepository.findByGameIdString(gameIdString)
                .orElseThrow(() -> new EntityNotFoundException("Game with: " + gameIdString + " does not exist."))
                .getStarted()) throw new IllegalArgumentException("You can't join, game has already started.");

        if (Boolean.TRUE.equals(userRepository.existsByUsername(requestedUsername))) {
            throw new IllegalArgumentException( "Error: " + joinGameRequest.getUsername() + " is already taken!");
        }

        User user = new User(requestedUsername, encoder.encode(gameIdString), gameIdString);
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_PLAYER).get());
        userRepository.save(user);

        // determines what starting color Player should have
        int c = 0;
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            c++;
            if (c == 8) {
                c = 0;
            }
        }

        Player player = new Player(gameIdString, user, username, Color.values()[c], true);
        player.setLobby(lobby);
        playerRepository.save(player);

        user.setPlayer(player);
        userRepository.save(user);

        lobby.getPlayers().add(player);
        lobbyRepository.save(lobby);

        return requestedUsername;
    }

    public String generateGameIdString() {
        String gameIdString ="placeholder";
        boolean uniqueGameIdString = false;

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
        return gameIdString;
    }
}
