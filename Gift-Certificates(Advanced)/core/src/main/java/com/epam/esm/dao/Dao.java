package com.epam.esm.dao;

import com.epam.esm.model.AbstractEntity;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface Dao<T extends AbstractEntity> {

    int insert(T entity);

    void delete(int id);

    void update(T entity);

    List<T> findAll();

    T findById(int id);
}
