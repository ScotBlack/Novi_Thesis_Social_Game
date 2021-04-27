package com.socialgame.alpha.repository;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    Optional<Lobby> findByGameIdString(String gameIdString);

//    @Query("SELECT p FROM Player p WHERE p.game.id = :gameId and p.name = :name")
//    Player findPlayerByNameAndLobbyId(@Param("lobbyId") Long gameId,
//                                      @Param("name") String name);

}