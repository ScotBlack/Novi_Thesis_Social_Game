package com.socialgame.alpha.repository;

import com.socialgame.alpha.domain.Player;
import com.socialgame.alpha.domain.Role;
import com.socialgame.alpha.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
