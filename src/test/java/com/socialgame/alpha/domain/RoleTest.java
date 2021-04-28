package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.Color;
import com.socialgame.alpha.domain.enums.ERole;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleTest {

    @Test
    void getId() {
        Role role = new Role();
        role.setId(1L);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Role.class,1L)).thenReturn(role);

        Long roleId = role.getId();

        assertEquals(1L, roleId);
    }

    @Test
    void getName() {
        Role role = new Role(ERole.ROLE_ADMIN);
        role.setId(1L);
        role.setName(ERole.ROLE_CAPTAIN);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.find(Role.class,1L)).thenReturn(role);

        ERole eRole = role.getName();

        assertEquals(ERole.ROLE_CAPTAIN, eRole);
    }
}