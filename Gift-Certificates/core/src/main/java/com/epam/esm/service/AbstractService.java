package com.epam.esm.service;

import com.epam.esm.model.AbstractEntity;

import java.util.List;

/**
 * Basic interface for service layer. Defines general methods for all services.
 */
public interface AbstractService<T extends AbstractEntity> {
    void insert(T entity);

    void delete(String name);

    void update(T entity);

    List<T> findAll();

    T findById(int id);
}
