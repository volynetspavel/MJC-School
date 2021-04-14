package com.epam.esm.dao;

import com.epam.esm.model.User;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public abstract class UserDao extends AbstractDao<User> {

    @Override
    public int insert(User entityDto) {
        return -1;
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public void update(User entityDto) {
    }

}
