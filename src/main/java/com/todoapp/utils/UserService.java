package com.todoapp.utils;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public final class UserService {

    public boolean isLoggedInUser(String userId) {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUserId = principal.getKeycloakSecurityContext().getToken().getSubject();
        return loggedInUserId.equals(userId);
    }

    public String getLoggedInUserId() {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getKeycloakSecurityContext().getToken().getSubject();
    }

}
