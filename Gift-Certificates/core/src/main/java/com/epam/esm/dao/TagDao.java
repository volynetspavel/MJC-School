package com.epam.esm.dao;

import com.epam.esm.model.Tag;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public abstract class TagDao extends AbstractDao<Tag>{

    public abstract void delete(int id);

    public abstract Tag findByName(String name);
}
