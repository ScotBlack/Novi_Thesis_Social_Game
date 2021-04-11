package com.socialgame.alpha.repository;

import com.socialgame.alpha.domain.Lobby;
import com.socialgame.alpha.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {

//    @Query("SELECT p FROM Player p WHERE p.game.id = :gameId and p.name = :name")
//    Player findPlayerByNameAndLobbyId(@Param("lobbyId") Long gameId,
//                                      @Param("name") String name);

}