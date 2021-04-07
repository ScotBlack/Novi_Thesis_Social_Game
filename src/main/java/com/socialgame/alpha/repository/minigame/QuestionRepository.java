package com.socialgame.alpha.repository.minigame;

import com.socialgame.alpha.domain.minigame.Question;

import javax.transaction.Transactional;

@Transactional
public interface QuestionRepository extends MiniGameBaseRepository<Question> {
}
