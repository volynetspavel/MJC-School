package com.epam.esm.service.crud;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

/**
 * This interface contains method for update dto-entity.
 */
public interface UpdatableService<T extends AbstractDto> {

    T update(T entityDto) throws ResourceNotFoundException, ResourceAlreadyExistException, ServiceException;
}
