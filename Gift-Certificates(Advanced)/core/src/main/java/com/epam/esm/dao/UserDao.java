package com.epam.esm.dao;

import com.epam.esm.model.User;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface UserDao extends Dao<User, Integer> {

    @Override
    default User insert(User entityDto) {
        return null;
    }

    @Override
    default void delete(User entityDto) {
    }

    @Override
    default void update(User entityDto) {
    }

    User findByEmail(String userEmail);
}
