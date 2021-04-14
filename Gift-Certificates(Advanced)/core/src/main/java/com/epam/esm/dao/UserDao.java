package com.epam.esm.dao;

import com.epam.esm.model.User;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface UserDao extends Dao<User> {

    @Override
    default int insert(User entityDto) {
        return -1;
    }

    @Override
    default void delete(int id) {
    }

    @Override
    default void update(User entityDto) {
    }
}
