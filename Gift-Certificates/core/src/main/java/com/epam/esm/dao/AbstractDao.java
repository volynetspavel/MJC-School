package com.epam.esm.dao;

import com.epam.esm.model.AbstractEntity;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public abstract class AbstractDao<T extends AbstractEntity> {
    public abstract void insert(T entity);

    public abstract void delete(String name);

    public abstract void update(T entity);

    public abstract List<T> findAll();

    public abstract T findById(int id);
}
