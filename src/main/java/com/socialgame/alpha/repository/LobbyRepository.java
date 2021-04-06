package com.socialgame.alpha.repository;

import com.socialgame.alpha.domain.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LobbyRepository extends JpaRepository<Lobby, Long> {

}