package com.socialgame.alpha.repository;

import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.game.id = :gameId and p.name = :name")
    Player findPlayerByNameAndGameId(@Param("gameId") Long gameId,
                                     @Param("name") String name);

    @Query("SELECT p FROM Player p WHERE p.game.id= :gameId ORDER BY p.id ASC")
    List<Player> findPlayersByGameId(@Param("gameId") Long gameId);

    @Query("SELECT p FROM Player p WHERE p.game.id= :gameId ORDER BY p.id ASC")
    List<Player> findPlayersByLobbyId(@Param("gameId") Long lobbyId);
}
