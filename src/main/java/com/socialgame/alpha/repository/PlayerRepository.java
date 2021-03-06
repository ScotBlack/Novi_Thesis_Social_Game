package com.socialgame.alpha.repository;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Boolean existsByName(String name);

    @Query("SELECT p FROM Player p WHERE p.lobby.id = :lobbyId and p.name = :name")
    Player findPlayerByNameAndLobbyId(@Param("lobbyId") Long gameId,
                                     @Param("name") String name);

    List<Player> findPlayersByGameIdString(String gameIdString);

    Optional<Player> findByName(String name);
}
