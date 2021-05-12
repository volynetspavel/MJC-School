package com.epam.esm.validation;

import com.epam.esm.constant.RoleValue;
import com.epam.esm.model.Role;
import com.epam.esm.security.JwtUser;
import com.epam.esm.security.SecurityUtil;

/**
 * Contains methods for checking the user's role.
 */
public class SecurityValidator {

    public static boolean isCurrentUserHasRoleUser() {
        JwtUser jwtUser = SecurityUtil.getJwtUser();
        Role role = new Role();
        role.setName(RoleValue.ROLE_USER);
        return jwtUser != null && jwtUser.getAuthorities().contains(role);
    }
}
