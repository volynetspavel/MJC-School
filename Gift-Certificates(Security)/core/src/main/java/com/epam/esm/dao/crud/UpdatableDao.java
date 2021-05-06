package com.epam.esm.dao.crud;

import com.epam.esm.model.AbstractEntity;

/**
 * This interface contains method for update entity.
 */
public interface UpdatableDao<T extends AbstractEntity> {

    void update(T entity);
}
