package com.epam.esm.dao.crud;

import com.epam.esm.model.AbstractEntity;

import java.util.List;

/**
 * This class is a basic class of dao-layer for interacting with database.
 */
public interface ReadableDao<T extends AbstractEntity, N extends Number> {

    List<T> findAll(int offset, int limit);

    T findById(N id);

    N getCount();
}
