package com.epam.esm.service;

import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.AbstractEntity;

import java.util.List;

/**
 * Basic interface for service layer. Defines general methods for all services.
 */
public interface AbstractService<T extends AbstractEntity> {
    void insert(T entity) throws ResourceAlreadyExistException;

    void delete(String name) throws ResourceNotFoundException;

    void update(T entity) throws ResourceNotFoundException, ResourceAlreadyExistException;

    List<T> findAll() throws ResourceNotFoundException;

    T findById(int id) throws ResourceNotFoundException;
}
