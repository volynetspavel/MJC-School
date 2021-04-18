package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Basic interface for service layer. Defines general methods for all services.
 */
public interface Service<T extends AbstractDto> {

    T insert(T entityDto) throws ResourceAlreadyExistException;

    T update(T entityDto) throws ResourceNotFoundException, ResourceAlreadyExistException;

    void delete(int id) throws ResourceNotFoundException;

    T findById(int id) throws ResourceNotFoundException;

    List<T> findAll(Map<String, String> params) throws ResourceNotFoundException;

    default void checkListOnEmptyOrNull(List<T> entities) throws ResourceNotFoundException {
        if (entities == null || entities.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found");
        }
    }
}
