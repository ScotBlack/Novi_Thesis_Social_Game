package com.socialgame.alpha.repository.minigame;

import com.socialgame.alpha.domain.minigame.MiniGame;

import javax.transaction.Transactional;

@Transactional
public interface MiniGameRepository extends MiniGameBaseRepository<MiniGame> {
}
