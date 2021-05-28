package com.epam.esm.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Contains methods for getting a user out of a security context.
 */
public class SecurityUtil {

    public static JwtUser getJwtUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        return principal instanceof JwtUser ? (JwtUser) principal : null;
    }

    public static Integer getJwtUserId() {
        JwtUser jwtUser = getJwtUser();
        return jwtUser != null ? jwtUser.getId() : null;
    }
}
