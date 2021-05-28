package com.epam.esm.service.crud;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

/**
 * This interface contains method for insert dto-entity.
 */
public interface InsertableService<T extends AbstractDto> {

    T insert(T entityDto) throws ResourceAlreadyExistException, ServiceException, ResourceNotFoundException;
}
