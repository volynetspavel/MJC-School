package com.epam.esm.dao.crud;

import com.epam.esm.model.AbstractEntity;

/**
 * This interface contains method for insert entity.
 */
public interface InsertableDao<T extends AbstractEntity> {

    T insert(T entity);
}
