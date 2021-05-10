package com.epam.esm.dao;

import com.epam.esm.model.Role;

/**
 * This class is a basic class of role dao-layer for interacting with database.
 */
public abstract class RoleDao {

    public abstract Role findByName(String roleName);
}
