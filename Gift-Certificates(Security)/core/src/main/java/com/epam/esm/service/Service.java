package com.epam.esm.service;

import com.epam.esm.constant.CodeException;
import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.model.AbstractEntity;

import java.util.List;
import java.util.Map;

/**
 * Basic interface for service layer. Defines general methods for all services.
 */
public interface Service<T extends AbstractDto, K extends AbstractEntity> {

    T insert(T entityDto) throws ResourceAlreadyExistException, ServiceException, ResourceNotFoundException;

    T update(T entityDto) throws ResourceNotFoundException, ResourceAlreadyExistException, ServiceException;

    void delete(int id) throws ResourceNotFoundException;

    T findById(int id) throws ResourceNotFoundException;

    List<T> findAll(Map<String, String> params) throws ValidationParametersException;

    default void checkEntityOnNull(K entitiy, int id) throws ResourceNotFoundException {
        if (entitiy == null) {
            throw new ResourceNotFoundException(CodeException.RESOURCE_NOT_FOUND, id);
        }
    }
}