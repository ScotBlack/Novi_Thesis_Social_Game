package com.socialgame.alpha.repository.minigame;


import com.socialgame.alpha.domain.minigame.MiniGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MiniGameBaseRepository <R extends MiniGame> extends JpaRepository<R, Long> {

}
