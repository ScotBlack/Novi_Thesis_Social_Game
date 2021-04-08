package com.socialgame.alpha.repository.minigame;


import com.socialgame.alpha.domain.minigame.MiniGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MiniGameBaseRepository <R extends MiniGame> extends JpaRepository<R, Long> {

//    @Query("select q from #{#question} as q where u.email = ?1 ")
//    R findQuestionById(Long Id);

}
