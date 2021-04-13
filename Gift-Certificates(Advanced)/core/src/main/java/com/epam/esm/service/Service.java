package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Basic interface for service layer. Defines general methods for all services.
 */
public interface Service<T extends AbstractDto> {

    T insert(T entityDto) throws ResourceAlreadyExistException;

    void delete(int id) throws ResourceNotFoundException;

    T update(T entityDto) throws ResourceNotFoundException, ResourceAlreadyExistException;

    List<T> findAll() throws ResourceNotFoundException;

    T findById(int id) throws ResourceNotFoundException;
}
