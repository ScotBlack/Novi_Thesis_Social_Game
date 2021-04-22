//package com.socialgame.alpha.security;
//
//import com.google.common.collect.Sets;
//
//import java.util.Set;
//
//import static com.socialgame.alpha.security.ApplicationUserPermission.*;
//
//public enum ApplicationUserRole {
//    ADMIN(Sets.newHashSet(GAME_WRITE, GAME_READ, TEAM_WRITE, TEAM_READ, PLAYER_WRITE, PLAYER_READ)),
//    HOST(Sets.newHashSet()),
//    CAPTAIN(Sets.newHashSet()),
//    PLAYER(Sets.newHashSet());
//
//    private final Set<ApplicationUserPermission> permissions;
//
//    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<ApplicationUserPermission> getPermissions() {
//        return permissions;
//    }
//}
