package com.epam.esm.service.crud;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.ResourceNotFoundException;

/**
 * This interface contains method for delete dto-entity.
 */
public interface DeletableService<T extends AbstractDto> {

    void delete(int id) throws ResourceNotFoundException;
}
