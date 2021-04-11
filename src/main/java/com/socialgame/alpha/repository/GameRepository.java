package com.socialgame.alpha.repository;



import com.socialgame.alpha.domain.Game;
import com.socialgame.alpha.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

//    @Query("SELECT p FROM Player p WHERE p.game.id = :gameId and p.name = :name")
//    Player findPlayerByNameAndGameId(@Param("gameId") Long gameId,
//                                     @Param("name") String name);

}
