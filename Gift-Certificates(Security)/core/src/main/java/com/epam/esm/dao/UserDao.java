package com.epam.esm.dao;

import com.epam.esm.dao.crud.InsertableDao;
import com.epam.esm.dao.crud.ReadableDao;
import com.epam.esm.model.User;

/**
 * This class is a basic class of user dao-layer for interacting with database.
 */
public abstract class UserDao implements ReadableDao<User, Integer>, InsertableDao<User> {

    public abstract User findByEmail(String userEmail);

}
