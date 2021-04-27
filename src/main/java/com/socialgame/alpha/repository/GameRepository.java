package com.socialgame.alpha.repository;



import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

   Boolean existsByGameIdString(String gameIdString);
   Optional<Game> findByGameIdString(String gameIdString);

//    @Query("SELECT p FROM Player p WHERE p.game.id = :gameId and p.name = :name")
//    Player findPlayerByNameAndGameId(@Param("gameId") Long gameId,
//                                     @Param("name") String name);

}
