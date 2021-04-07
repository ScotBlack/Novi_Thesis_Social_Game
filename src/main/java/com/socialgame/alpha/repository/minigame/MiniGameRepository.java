package com.socialgame.alpha.repository.minigame;

import com.socialgame.alpha.domain.minigame.MiniGame;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface MiniGameRepository extends MiniGameBaseRepository<MiniGame> {

}
