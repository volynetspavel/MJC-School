package com.epam.esm.dao;

import com.epam.esm.model.AbstractEntity;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface Dao<T extends AbstractEntity, N extends Number> {

    T insert(T entity);

    void update(T entity);

    void delete(T entity);

    List<T> findAll(int offset, int limit);

    T findById(N id);

    N getCount();
}
