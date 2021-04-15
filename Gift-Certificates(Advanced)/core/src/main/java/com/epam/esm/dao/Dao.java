package com.epam.esm.dao;

import com.epam.esm.model.AbstractEntity;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface Dao<T extends AbstractEntity> {

    T insert(T entity);

    void update(T entity);

    void delete(T entity);

    T findById(int id);

    List<T> findAll();
}
