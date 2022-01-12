package com.todoapp.utils;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UserUtil {

    private UserUtil() {}

    public static boolean isLoggedInUser(String userId) {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUserId = principal.getKeycloakSecurityContext().getToken().getSubject();
        return loggedInUserId.equals(userId);
    }

    public static String getLoggedInUserId() {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getKeycloakSecurityContext().getToken().getSubject();
    }

}
