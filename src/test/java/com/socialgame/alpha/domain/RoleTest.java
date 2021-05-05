package com.socialgame.alpha.domain;


import com.socialgame.alpha.domain.enums.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RoleTest {

    Role role;

    @BeforeEach
    void setUp() {
        role = new Role(ERole.ROLE_ADMIN);
        role.setId(1L);
        role.setName(ERole.ROLE_CAPTAIN);
    }

    @Test
    void rolePropertiesTest() {
        assertEquals(1L, role.getId());
        assertEquals(ERole.ROLE_CAPTAIN, role.getName());
    }
}