package com.epam.esm.mapper;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.model.AbstractEntity;

/**
 * Interface for transferring between DTO-class and entity class.
 */
public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {
    E toEntity(D dto);

    D toDto(E entity);
}
