package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Basic interface for service layer. Defines general methods for all services.
 */
public interface AbstractService<T extends AbstractDto> {
    void insert(T entityDto) throws ResourceAlreadyExistException;

    void delete(int id) throws ResourceNotFoundException;

    void update(T entityDto) throws ResourceNotFoundException, ResourceAlreadyExistException;

    List<T> findAll() throws ResourceNotFoundException;

    T findById(int id) throws ResourceNotFoundException;
}
