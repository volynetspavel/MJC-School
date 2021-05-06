package com.epam.esm.dao.crud;

import com.epam.esm.model.AbstractEntity;

/**
 * This interface contains method for delete entity.
 */
public interface DeletableDao<T extends AbstractEntity> {

    void delete(T entity);
}
